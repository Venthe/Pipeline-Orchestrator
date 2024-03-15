package eu.venthe.pipeline.orchestrator.projects_source.application;

import eu.venthe.pipeline.orchestrator.plugins.projects.ReadProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.projects_source.domain.ProjectSourceConfigurationId;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ProjectsSourceConfigurationService {

    ProjectSourceConfigurationId addProjectSourceConfiguration(String id, String sourceType, Map<String, String> properties);

    void synchronizeProjects(ProjectSourceConfigurationId projectSourceConfigurationId);

    void removeProjectSourceConfiguration(ProjectSourceConfigurationId projectSourceConfigurationId);

    Set<ReadProjectSourceConfigurationDto> listConfigurations();

    Optional<ReadProjectSourceConfigurationDto> getConfiguration(ProjectSourceConfigurationId projectSourceConfigurationId);
}
