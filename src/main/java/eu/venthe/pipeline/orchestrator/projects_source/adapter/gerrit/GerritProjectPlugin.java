package eu.venthe.pipeline.orchestrator.projects_source.adapter.gerrit;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.ProjectSourceAdapter;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.RepositoryReader;
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
public class GerritProjectPlugin implements ProjectSourceAdapter {
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
    public RepositoryReader getVersionControlSystem(Map<String, String> properties) {
        return new GerritVersionControlSystem();
    }

    @Override
    public Map<String, Property> getRequiredProperties() {
        return List.of(
                        ProjectSourceAdapter.Property.builder().type("string").value("basePath").required(true).build(),
                        ProjectSourceAdapter.Property.builder().type("string").value("username").build(),
                        ProjectSourceAdapter.Property.builder().type("string").value("password").masked(true).build()
                ).stream()
                .collect(Collectors.toMap(ProjectSourceAdapter.Property::getValue, UnaryOperator.identity()));
    }
}
