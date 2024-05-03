package eu.venthe.pipeline.orchestrator.projects_source.adapter.plugin.controlled_provider;

import eu.venthe.pipeline.orchestrator.projects_source.adapter._archive.ProjectSourceAdapter;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.projects_source.adapter._archive.Property;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.ProjectDataAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ControlledTestProviderPlugin implements ProjectSourceAdapter {
    public static final String SOURCE_TYPE = "test_provider";

    private final ControlledTestVersionControlSystem versionControlSystem;
    private final ControlledTestProjectProvider projectProvider;

    @Override
    public String getSourceType() {
        return SOURCE_TYPE;
    }

    @Override
    public ProjectProvider getProjectProvider(Map<String, String> properties) {
        return projectProvider;
    }

    @Override
    public ProjectDataAccessService getVersionControlSystem(Map<String, String> properties) {
        return versionControlSystem;
    }

    @Override
    public Map<String, Property> getRequiredProperties() {
        return List.of(
                        Property.builder().type("string").value("url").required(true).defaultValue("http://localhost:80").build()
                ).stream()
                .collect(Collectors.toMap(Property::getValue, UnaryOperator.identity()));
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
