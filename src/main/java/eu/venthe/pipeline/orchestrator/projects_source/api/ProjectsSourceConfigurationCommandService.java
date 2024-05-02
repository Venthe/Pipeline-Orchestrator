package eu.venthe.pipeline.orchestrator.projects_source.api;

import java.util.Map;

public interface ProjectsSourceConfigurationCommandService {

    String addProjectSourceConfiguration(String id, String sourceType, Map<String, String> properties);

    void synchronizeProject(String projectSourceConfigurationId);

    void removeProjectSourceConfiguration(String projectSourceConfigurationId);

    void synchronizeProjects();
}
