/*
package eu.venthe.pipeline.orchestrator.projects_source.adapter.plugin.gerrit._archive2;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.pipeline.orchestrator.projects_source.adapter._archive.ProjectSourceAdapter;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.projects_source.adapter._archive.Property;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.ProjectDataAccessService;
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
    public ProjectDataAccessService getVersionControlSystem(Map<String, String> properties) {
        return new GerritVersionControlSystem();
    }

    @Override
    public Map<String, Property> getRequiredProperties() {
        return List.of(
                        Property.builder().type("string").value("basePath").required(true).build(),
                        Property.builder().type("string").value("username").build(),
                        Property.builder().type("string").value("password").masked(true).build()
                ).stream()
                .collect(Collectors.toMap(Property::getValue, UnaryOperator.identity()));
    }
}
*/
