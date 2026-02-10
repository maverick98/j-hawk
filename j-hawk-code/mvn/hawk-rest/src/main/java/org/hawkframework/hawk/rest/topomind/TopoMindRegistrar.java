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

@Component
public class TopoMindRegistrar implements ApplicationRunner {

    @Value("${topomind.url}")
    private String topomindUrl;

    @Value("${topomind.service-name}")
    private String serviceName;

    @Value("${topomind.base-url}")
    private String baseUrl;

    @Override
    public void run(ApplicationArguments args) {
        new Thread(this::registerWithTopoMind).start();
    }

    private void registerWithTopoMind() {

        try {
            Thread.sleep(2000);

            RestTemplate restTemplate = new RestTemplate();

            // 1 Register connector
            restTemplate.postForObject(
                    topomindUrl + "/register-connector",
                    Map.of(
                            "name", serviceName,
                            "type", "rest",
                            "base_url", baseUrl
                    ),
                    String.class
            );

            // 2 Load plugin + tool metadata
            IHawkPluginService pluginService =
                    AppContainer.getInstance().getBean(HawkPluginServiceImpl.class);

            Set<HawkPlugin> installedPlugins = pluginService.findInstalledPlugins();

            if (installedPlugins == null || installedPlugins.isEmpty()) {
                throw new RuntimeException("No installed plugins found.");
            }

            // Assume only one plugin
            HawkPlugin plugin = installedPlugins.iterator().next();
            HawkPluginMetaData meta = plugin.getPluginMetaData();

            if (meta.getAi() == null || meta.getAi().getTool() == null
                    || meta.getAi().getTool().isEmpty()) {
                throw new RuntimeException("No AI tool definition found in metadata.");
            }

            var tool = meta.getAi().getTool().get(0); // assume one tool

            // 3 Load prompt from plugin folder
            String promptPath = plugin.getPluginHome()
                    + File.separator
                    + tool.getPromptFile();

            File promptFile = new File(promptPath);

            if (!promptFile.exists()) {
                throw new RuntimeException("Prompt file not found: " + promptPath);
            }

            String prompt = Files.readString(promptFile.toPath());

            // 4 Build schemas dynamically
            Map<String, Object> inputSchema = new HashMap<>();
            tool.getInputSchema().getField().forEach(field ->
                    inputSchema.put(field.getName(), field.getType())
            );

            Map<String, Object> outputSchema = new HashMap<>();
            tool.getOutputSchema().getField().forEach(field ->
                    outputSchema.put(field.getName(), field.getType())
            );

            // 5 Register tool dynamically
            restTemplate.postForObject(
                    topomindUrl + "/register-tool",
                    Map.of(
                            "name", tool.getName(),
                            "description", tool.getDescription(),
                            "input_schema", inputSchema,
                            "output_schema", outputSchema,
                            "connector", serviceName,
                            "prompt", prompt,
                            "strict", tool.isStrict()
                    ),
                    String.class
            );

            System.out.println("✔ Tool registered from plugin metadata: " + tool.getName());

        } catch (Exception e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
