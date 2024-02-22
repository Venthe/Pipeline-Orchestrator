package eu.venthe.pipeline.orchestrator.configuration;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Data
@ConfigurationProperties(prefix = "plugins")
public class PluginConfiguration {
    ConfigurationContent jobExecutor;
    Map<String, ConfigurationContent> versionControlSystem;
    Map<String, ConfigurationContent> projectProvider;

    @RequiredArgsConstructor
    @Data
    public static class ConfigurationContent {
        String type;
        Map<String, ?> properties;
    }
}
