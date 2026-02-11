package org.hawkframework.hawk.rest.topomind;

import org.common.di.AppContainer;
import org.hawk.plugin.HawkPlugin;
import org.hawk.plugin.HawkPluginServiceImpl;
import org.hawk.plugin.IHawkPluginService;
import org.hawk.plugin.metadata.HawkPluginMetaData;
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

    @Value("${topomind.execution-model:mistral:latest}")
    private String executionModel;

    @Override
    public void run(ApplicationArguments args) {
        new Thread(this::registerWithTopoMind).start();
    }

    private void registerWithTopoMind() {
        try {
            waitForServer();

            RestTemplate restTemplate = new RestTemplate();

            registerConnector(restTemplate);

            HawkPlugin plugin = loadPlugin();

            var tool = extractToolMetadata(plugin);

            String prompt = loadPrompt(plugin, tool.getPromptFile());

            Map<String, Object> inputSchema = buildSchema(tool.getInputSchema());
            Map<String, Object> outputSchema = buildSchema(tool.getOutputSchema());

            registerTool(restTemplate, tool, prompt, inputSchema, outputSchema);

            System.out.println("✔ Tool registered: " + tool.getName());
            System.out.println("✔ Execution model: " + executionModel);

        } catch (Exception e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------------------
    // Step 1: Wait for TopoMind
    // ------------------------------
    private void waitForServer() throws InterruptedException {
        Thread.sleep(2000);
    }

    // ------------------------------
    // Step 2: Register Connector
    // ------------------------------
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

    // ------------------------------
    // Step 3: Load Plugin
    // ------------------------------
    private HawkPlugin loadPlugin() {
        try {
            IHawkPluginService pluginService
                    = AppContainer.getInstance().getBean(HawkPluginServiceImpl.class);

            Set<HawkPlugin> plugins = pluginService.findInstalledPlugins();

            if (plugins == null || plugins.isEmpty()) {
                throw new RuntimeException("No installed plugins found.");
            }

            return plugins.iterator().next();

        } catch (HawkPluginException | HawkEventException e) {
            throw new RuntimeException("Plugin initialization failed: " + e.getMessage(), e);
        }
    }

    // ------------------------------
    // Step 4: Extract Tool Metadata
    // ------------------------------
    private org.hawk.plugin.metadata.Tool extractToolMetadata(HawkPlugin plugin) {

        HawkPluginMetaData meta = plugin.getPluginMetaData();

        if (meta.getAi() == null
                || meta.getAi().getTool() == null
                || meta.getAi().getTool().isEmpty()) {

            throw new RuntimeException("No AI tool definition found.");
        }

        return meta.getAi().getTool().get(0);
    }

    // ------------------------------
    // Step 5: Load Prompt File
    // ------------------------------
    private String loadPrompt(HawkPlugin plugin, String promptFileName) throws Exception {

        String path = plugin.getPluginHome()
                + File.separator
                + promptFileName;

        File file = new File(path);

        if (!file.exists()) {
            throw new RuntimeException("Prompt file not found: " + path);
        }

        return Files.readString(file.toPath());
    }

    // ------------------------------
    // Step 6: Build Schema Map
    // ------------------------------
    private Map<String, Object> buildSchema(org.hawk.plugin.metadata.Schema schema) {

        Map<String, Object> map = new HashMap<>();

        schema.getField().forEach(field
                -> map.put(field.getName(), field.getType())
        );

        return map;
    }

    // ------------------------------
    // Step 7: Register Tool
    // ------------------------------
    private void registerTool(
            RestTemplate restTemplate,
            org.hawk.plugin.metadata.Tool tool,
            String prompt,
            Map<String, Object> inputSchema,
            Map<String, Object> outputSchema
    ) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("name", tool.getName());
        payload.put("description", tool.getDescription());
        payload.put("input_schema", inputSchema);
        payload.put("output_schema", outputSchema);
        payload.put("connector", serviceName);
        payload.put("prompt", prompt);
        payload.put("strict", tool.isStrict());
        payload.put("execution_model", executionModel);

        restTemplate.postForObject(
                topomindUrl + "/register-tool",
                payload,
                String.class
        );
    }
}
