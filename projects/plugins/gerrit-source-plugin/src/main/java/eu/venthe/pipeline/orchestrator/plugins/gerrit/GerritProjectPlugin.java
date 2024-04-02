package eu.venthe.pipeline.orchestrator.plugins.gerrit;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectPlugin;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystemProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "gerrit", name = "enabled", havingValue = "true")
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
    public VersionControlSystemProvider getVersionControlSystem(Map<String, String> properties) {
        return new GerritVersionControlSystem();
    }

    @Override
    public Map<String, Property> getRequiredProperties() {
        return List.of(
                        ProjectPlugin.Property.builder().type("string").value("basePath").required(true).build(),
                        ProjectPlugin.Property.builder().type("string").value("username").build(),
                        ProjectPlugin.Property.builder().type("string").value("password").masked(true).build()
                ).stream()
                .collect(Collectors.toMap(ProjectPlugin.Property::getValue, UnaryOperator.identity()));
    }
}
