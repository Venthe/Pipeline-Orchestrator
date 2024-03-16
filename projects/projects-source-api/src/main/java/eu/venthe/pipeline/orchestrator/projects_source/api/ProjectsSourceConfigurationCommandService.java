package eu.venthe.pipeline.orchestrator.projects_source.api;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ProjectsSourceConfigurationCommandService {

    String addProjectSourceConfiguration(String id, String sourceType, Map<String, String> properties);

    void synchronizeProjects(String projectSourceConfigurationId);

    void removeProjectSourceConfiguration(String projectSourceConfigurationId);
}
