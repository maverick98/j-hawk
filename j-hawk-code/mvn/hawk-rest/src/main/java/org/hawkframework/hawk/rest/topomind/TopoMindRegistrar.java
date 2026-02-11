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

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.commons.event.exception.HawkEventException;
import org.hawk.plugin.exception.HawkPluginException;

@Component
public class TopoMindRegistrar implements ApplicationRunner {

    @Value("${topomind.url}")
    private String topomindUrl;

    @Value("${topomind.service-name}")
    private String serviceName;

    @Value("${topomind.base-url}")
    private String baseUrl;

    @Value("${topomind.execution-model:deepseekcoder:latest}")
    private String executionModel;

    @Override
    public void run(ApplicationArguments args) {
        new Thread(this::registerWithTopoMind).start();
    }

    private void registerWithTopoMind() {
        try {
            waitForServer();

            RestTemplate restTemplate = new RestTemplate();

            // 1 Register REST connector (hawk_runtime)
            registerConnector(restTemplate);

            // 2 Load plugin to fetch Hawk DSL prompt
            HawkPlugin plugin = loadPlugin();
            String prompt = loadPrompt(plugin);

            // 3 Register compileHawk (LLM tool)
            registerCompileTool(restTemplate, prompt);

            // 4 Register executeHawk (REST tool)
            registerExecuteTool(restTemplate);

            System.out.println("✔ compileHawk registered");
            System.out.println("✔ executeHawk registered");
            System.out.println("✔ Execution model: " + executionModel);

        } catch (Exception e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --------------------------------------------------
    // Step 1: Wait for TopoMind
    // --------------------------------------------------
    private void waitForServer() throws InterruptedException {
        Thread.sleep(2000);
    }

    // --------------------------------------------------
    // Step 2: Register REST Connector
    // --------------------------------------------------
    private void registerConnector(RestTemplate restTemplate) {
        restTemplate.postForObject(
                topomindUrl + "/register-connector",
                Map.of(
                        "name", serviceName,
                        "type", "rest",
                        "base_url", baseUrl
                ),
                String.class
        );
    }

    // --------------------------------------------------
    // Step 3: Load Plugin
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
    // Step 4: Load Prompt File
    // --------------------------------------------------
    private String loadPrompt(HawkPlugin plugin) throws Exception {

        // We assume first AI tool contains prompt file reference
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
    // Step 5: Register compileHawk (LLM)
    // --------------------------------------------------
    private void registerCompileTool(RestTemplate restTemplate, String prompt) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("name", "compileHawk");
        payload.put("description", "Translate natural language into Hawk DSL");
        payload.put("input_schema", Map.of("query", "string"));
        payload.put("output_schema", Map.of("code", "string"));
        payload.put("connector", "llm");                 //  LLM connector
        payload.put("prompt", prompt);
        payload.put("strict", true);
        payload.put("execution_model", executionModel); // deepseekcoder

        restTemplate.postForObject(
                topomindUrl + "/register-tool",
                payload,
                String.class
        );
    }

    // --------------------------------------------------
    // Step 6: Register executeHawk (REST)
    // --------------------------------------------------
    private void registerExecuteTool(RestTemplate restTemplate) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("name", "executeHawk");
        payload.put("description", "Execute Hawk DSL via Hawk Runtime");
        payload.put("input_schema", Map.of("code", "string"));
        payload.put("output_schema", Map.of("result", "any"));
        payload.put("connector", serviceName);  // hawk_runtime
        payload.put("strict", false);
        payload.put("execution_model", "");     // No LLM here

        restTemplate.postForObject(
                topomindUrl + "/register-tool",
                payload,
                String.class
        );
    }
}
