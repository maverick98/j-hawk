package org.hawkframework.hawk.rest.topomind;

import org.common.di.AppContainer;
import org.hawk.plugin.HawkPlugin;
import org.hawk.plugin.HawkPluginServiceImpl;
import org.hawk.plugin.IHawkPluginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.commons.event.exception.HawkEventException;
import org.hawk.plugin.exception.HawkPluginException;

@Component
public class TopoMindRegistrar implements ApplicationRunner {

    private static final Logger logger =
            LoggerFactory.getLogger(TopoMindRegistrar.class);

    @Value("${topomind.url}")
    private String topomindUrl;

    @Value("${topomind.service-name}")
    private String serviceName;

    @Value("${topomind.base-url}")
    private String baseUrl;

    @Value("${topomind.llm-backend:groq}")
    private String llmBackend;

    @Value("${topomind.execution-model:llama-3.1-8b-instant}")
    private String executionModel;

    @Override
    public void run(ApplicationArguments args) {
        Thread thread = new Thread(this::registerWithTopoMind, "TopoMind-Registrar-Thread");
        thread.setDaemon(true);
        thread.start();
    }

    private void registerWithTopoMind() {
        try {
            logger.info("Starting TopoMind registration...");
            logger.info("LLM Backend: {}", llmBackend);
            logger.info("Execution Model: {}", executionModel);

            waitForServer();

            RestTemplate restTemplate = new RestTemplate();

            registerConnector(restTemplate);

            HawkPlugin plugin = loadPlugin();
            String prompt = loadPrompt(plugin);

            registerCompileTool(restTemplate, prompt);
            registerExecuteTool(restTemplate);

            logger.info("✔ compileHawk registered");
            logger.info("✔ executeHawk registered");
            logger.info("✔ Registration completed successfully");

        } catch (Exception e) {
            logger.error("❌ Registration failed: {}", e.getMessage(), e);
        }
    }

    // --------------------------------------------------
    // Step 1: Wait for TopoMind (Health Polling)
    // --------------------------------------------------
    private void waitForServer() throws InterruptedException {

        RestTemplate restTemplate = new RestTemplate();
        int retries = 20;

        while (retries-- > 0) {
            try {
                restTemplate.getForObject(topomindUrl + "/health", String.class);
                logger.info("✔ TopoMind is ready");
                return;
            } catch (Exception ignored) {
                logger.info("Waiting for TopoMind...");
                Thread.sleep(1000);
            }
        }

        throw new RuntimeException("TopoMind server not reachable after retries.");
    }

    // --------------------------------------------------
    // Step 2: Register REST Connector (Idempotent)
    // --------------------------------------------------
    private void registerConnector(RestTemplate restTemplate) {
        try {
            restTemplate.postForObject(
                    topomindUrl + "/register-connector",
                    Map.of(
                            "name", serviceName,
                            "type", "rest",
                            "base_url", baseUrl
                    ),
                    String.class
            );
            logger.info("✔ Connector registered: {}", serviceName);
        } catch (RestClientException e) {
            logger.warn("⚠ Connector may already exist. Continuing...");
        }
    }

    // --------------------------------------------------
    // Step 3: Load Plugin Safely
    // --------------------------------------------------
    private HawkPlugin loadPlugin() {

        try {
            IHawkPluginService pluginService =
                    AppContainer.getInstance().getBean(HawkPluginServiceImpl.class);

            Set<HawkPlugin> plugins = pluginService.findInstalledPlugins();

            if (plugins == null || plugins.isEmpty()) {
                throw new RuntimeException("No installed plugins found.");
            }

            return plugins.iterator().next();

        } catch (HawkPluginException | HawkEventException e) {
            throw new RuntimeException("Plugin initialization failed: " + e.getMessage(), e);
        }
    }

    // --------------------------------------------------
    // Step 4: Load Prompt File (Validated)
    // --------------------------------------------------
    private String loadPrompt(HawkPlugin plugin) throws Exception {

        if (plugin.getPluginMetaData() == null ||
                plugin.getPluginMetaData().getAi() == null ||
                plugin.getPluginMetaData().getAi().getTool() == null ||
                plugin.getPluginMetaData().getAi().getTool().isEmpty()) {
            throw new RuntimeException("AI tool metadata missing in plugin.");
        }

        String promptFileName = plugin.getPluginMetaData()
                .getAi()
                .getTool()
                .get(0)
                .getPromptFile();

        String path = plugin.getPluginHome()
                + File.separator
                + promptFileName;

        File file = new File(path);

        if (!file.exists()) {
            throw new RuntimeException("Prompt file not found: " + path);
        }

        return Files.readString(file.toPath());
    }

    // --------------------------------------------------
    // Step 5: Register compileHawk (LLM Tool)
    // --------------------------------------------------
    private void registerCompileTool(RestTemplate restTemplate, String prompt) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("name", "compileHawk");
        payload.put("description", "Translate natural language into Hawk DSL");
        payload.put("input_schema", Map.of("query", "string"));
        payload.put("output_schema", Map.of("code", "string"));
        payload.put("connector", "llm");
        payload.put("prompt", prompt);
        payload.put("strict", true);
        payload.put("execution_model", executionModel);

        try {
            restTemplate.postForObject(
                    topomindUrl + "/register-tool",
                    payload,
                    String.class
            );
        } catch (RestClientException e) {
            logger.warn("⚠ compileHawk may already be registered. Continuing...");
        }
    }

    // --------------------------------------------------
    // Step 6: Register executeHawk (REST Tool)
    // --------------------------------------------------
    private void registerExecuteTool(RestTemplate restTemplate) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("name", "executeHawk");
        payload.put("description", "Execute Hawk DSL via Hawk Runtime");
        payload.put("input_schema", Map.of("code", "string"));
        payload.put("output_schema", Map.of("result", "any"));
        payload.put("connector", serviceName);
        payload.put("strict", false);
        payload.put("execution_model", "");

        try {
            restTemplate.postForObject(
                    topomindUrl + "/register-tool",
                    payload,
                    String.class
            );
        } catch (RestClientException e) {
            logger.warn("⚠ executeHawk may already be registered. Continuing...");
        }
    }
}
