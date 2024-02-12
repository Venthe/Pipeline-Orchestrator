package eu.venthe.pipeline.orchestrator.plugins.plugin.gerrit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "plugins.gerrit")
public class GerritBindingConfiguration {
    String url;
    String username;
    String password;
//    Binding binding;
//    Set<String> ignoredEvents = new HashSet<>();
//    @Data
//    public static class Binding {
//        String topic;
//    }
}
