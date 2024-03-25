package eu.venthe.pipeline.orchestrator.plugins.controlled_provider;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectPlugin;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystemProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ControlledTestProviderPlugin implements ProjectPlugin {
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
    public VersionControlSystemProvider getVersionControlSystem(Map<String, String> properties) {
        return versionControlSystem;
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
