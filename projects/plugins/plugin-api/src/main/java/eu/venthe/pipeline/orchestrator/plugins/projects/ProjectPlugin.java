package eu.venthe.pipeline.orchestrator.plugins.projects;

import java.util.Map;

public interface ProjectPlugin {
    String getSourceType();
    ProjectProvider getProjectProvider(Map<String, String> properties);
    VersionControlSystemProvider getVersionControlSystem(Map<String, String> properties);
}
