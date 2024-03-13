package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.plugins.projects.CreateProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.plugins.projects.ReadProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSourceConfigurationId;

import java.util.Optional;
import java.util.Set;

public interface ProjectsSourceConfigurationService {

    ProjectSourceConfigurationId addProjectSourceConfiguration(CreateProjectSourceConfigurationDto configuration);

    void synchronizeProjects(ProjectSourceConfigurationId projectSourceConfigurationId);

    void removeProjectSourceConfiguration(ProjectSourceConfigurationId projectSourceConfigurationId);

    Set<ReadProjectSourceConfigurationDto> listConfigurations();

    Optional<ReadProjectSourceConfigurationDto> getConfiguration(ProjectSourceConfigurationId projectSourceConfigurationId);
}
