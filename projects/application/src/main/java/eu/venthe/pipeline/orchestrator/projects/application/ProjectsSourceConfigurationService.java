package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSourceConfigurationId;

import java.util.Optional;
import java.util.Set;

public interface ProjectsSourceConfigurationService {

    ProjectSourceConfigurationId addProjectSourceConfiguration(ProjectSourceConfigurationDto configuration);

    void synchronizeProjects(ProjectSourceConfigurationId projectSourceConfigurationId);

    void removeProjectSourceConfiguration(ProjectSourceConfigurationId projectSourceConfigurationId);

    Set<ProjectSourceConfigurationDto> listConfigurations();

    Optional<ProjectSourceConfigurationDto> getConfiguration(ProjectSourceConfigurationId projectSourceConfigurationId);
}
