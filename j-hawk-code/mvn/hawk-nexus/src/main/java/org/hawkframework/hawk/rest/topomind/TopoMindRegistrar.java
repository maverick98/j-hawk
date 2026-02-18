package org.hawkframework.hawk.rest.topomind;

import org.common.di.AppContainer;
import org.hawk.plugin.HawkPlugin;
import org.hawk.plugin.HawkPluginServiceImpl;
import org.hawk.plugin.IHawkPluginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

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
            logger.info("🚀 Starting TopoMind registration");
            logger.info("LLM Backend: {}", llmBackend);
            logger.info("Execution Model: {}", executionModel);

            waitForServer();

            RestTemplate restTemplate = new RestTemplate();

            registerConnector(restTemplate);
            verifyConnectorStatus(restTemplate);

            HawkPlugin plugin = loadPlugin();
            String prompt = loadPrompt(plugin);

            registerCompileTool(restTemplate, prompt);
            registerExecuteTool(restTemplate);

            logger.info("✅ compileHawk registered");
            logger.info("✅ executeHawk registered");
            logger.info("🎯 Hawk registration completed successfully");

        } catch (Exception e) {
            logger.error("❌ Registration failed: {}", e.getMessage(), e);
        }
    }

    // --------------------------------------------------
    // Wait for TopoMind
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
    // Register Connector (Lifecycle-aware)
    // --------------------------------------------------
    private void registerConnector(RestTemplate restTemplate) {

        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            topomindUrl + "/register-connector",
                            Map.of(
                                    "name", serviceName,
                                    "type", "rest",
                                    "base_url", baseUrl
                            ),
                            String.class
                    );

            logger.info("Connector registration HTTP status: {}",
                    response.getStatusCode());

        } catch (RestClientException e) {
            throw new RuntimeException("Connector registration failed", e);
        }
    }

    // --------------------------------------------------
    // Verify Connector Status (Do NOT auto-deploy)
    // --------------------------------------------------
    private void verifyConnectorStatus(RestTemplate restTemplate) {

        try {
            ResponseEntity<Map> response =
                    restTemplate.getForEntity(
                            topomindUrl + "/connectors",
                            Map.class
                    );

            Map body = response.getBody();

            if (body == null || !body.containsKey("connectors")) {
                throw new RuntimeException("Invalid /connectors response");
            }

            List<Map<String, Object>> connectors =
                    (List<Map<String, Object>>) body.get("connectors");

            for (Map<String, Object> connector : connectors) {

                if (serviceName.equals(connector.get("name"))) {

                    String status = (String) connector.get("status");

                    logger.info("Connector '{}' status: {}",
                            serviceName, status);

                    if ("inactive".equalsIgnoreCase(status)) {
                        throw new RuntimeException(
                                "Connector is inactive in TopoMind. "
                                + "Deployment must be enabled centrally."
                        );
                    }

                    return;
                }
            }

            throw new RuntimeException(
                    "Connector not found after registration."
            );

        } catch (Exception e) {
            throw new RuntimeException("Connector verification failed", e);
        }
    }

    // --------------------------------------------------
    // Load Plugin
    // --------------------------------------------------
    private HawkPlugin loadPlugin() {

        try {
            IHawkPluginService pluginService =
                    AppContainer.getInstance().getBean(HawkPluginServiceImpl.class);

            Set<HawkPlugin> plugins =
                    pluginService.findInstalledPlugins();

            if (plugins == null || plugins.isEmpty()) {
                throw new RuntimeException("No installed plugins found.");
            }

            return plugins.iterator().next();

        } catch (HawkPluginException | HawkEventException e) {
            throw new RuntimeException("Plugin initialization failed", e);
        }
    }

    // --------------------------------------------------
    // Load Prompt
    // --------------------------------------------------
    private String loadPrompt(HawkPlugin plugin) throws Exception {

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
    // Register compileHawk
    // --------------------------------------------------
    private void registerCompileTool(RestTemplate restTemplate, String prompt) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("name", "compileHawk");
        payload.put("description", "Translate natural language into Hawk DSL");
        payload.put("input_schema", Map.of("query", "string"));
        payload.put("output_schema", Map.of("hawk_dsl", "string"));
        payload.put("connector", "llm");
        payload.put("prompt", prompt);
        payload.put("strict", true);
        payload.put("execution_model", executionModel);

        restTemplate.postForObject(
                topomindUrl + "/register-tool",
                payload,
                String.class
        );
    }

    // --------------------------------------------------
    // Register executeHawk
    // --------------------------------------------------
    private void registerExecuteTool(RestTemplate restTemplate) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("name", "executeHawk");
        payload.put("description", "Execute Hawk DSL via Hawk Runtime");
        payload.put("input_schema", Map.of("hawk_dsl", "string"));
        payload.put("output_schema", Map.of("result", "any"));
        payload.put("connector", serviceName);
        payload.put("strict", false);

        restTemplate.postForObject(
                topomindUrl + "/register-tool",
                payload,
                String.class
        );
    }
}
