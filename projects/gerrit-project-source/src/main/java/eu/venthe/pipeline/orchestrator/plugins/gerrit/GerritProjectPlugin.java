package eu.venthe.pipeline.orchestrator.plugins.gerrit;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectPlugin;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystem;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GerritProjectPlugin implements ProjectPlugin {
    @Override
    public String getSourceType() {
        return "gerrit";
    }

    @Override
    public ProjectProvider getProjectProvider(Map<String, String> properties) {
        return new GerritProjectProvider();
    }

    @Override
    public VersionControlSystem getVersionControlSystem(Map<String, String> properties) {
        return new GerritVersionControlSystem();
    }
}
