package eu.venthe.pipeline.orchestrator.plugins.gerrit;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectPlugin;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GerritProjectPlugin implements ProjectPlugin {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public String getSourceType() {
        return "gerrit";
    }

    @Override
    public ProjectProvider getProjectProvider(Map<String, String> properties) {
        return new GerritProjectProvider(restTemplate, objectMapper, properties);
    }

    @Override
    public VersionControlSystem getVersionControlSystem(Map<String, String> properties) {
        return new GerritVersionControlSystem();
    }
}
