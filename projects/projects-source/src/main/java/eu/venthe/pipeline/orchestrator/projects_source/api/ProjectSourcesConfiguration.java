package eu.venthe.pipeline.orchestrator.projects_source.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties("plugins")
@Data
public class ProjectSourcesConfiguration {
    private Map<String, ProjectSourceConfiguration> projectSources;

    @Data
    public static class ProjectSourceConfiguration {
        public String sourcePluginId;
        public Map<String, String> properties = new HashMap<>();
    }
}
