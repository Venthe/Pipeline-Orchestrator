package eu.venthe.pipeline.orchestrator.projects.source_configuration.application;

import java.util.Map;

public interface ProjectsSourceConfigurationCommandService {

    String addProjectSourceConfiguration(String id, String sourceType, Map<String, String> properties);

    void synchronizeProject(String projectSourceConfigurationId);

    void removeProjectSourceConfiguration(String projectSourceConfigurationId);

    void synchronizeProjects();
}
