package eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application;

import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.ProjectSourceConfiguration;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.ProjectSourceConfigurationId;

import java.util.Collection;
import java.util.Optional;

public interface ProjectsSourceRepository {
    void save(ProjectSourceConfiguration configuration);

    Optional<ProjectSourceConfiguration> find(ProjectSourceConfigurationId projectSourceConfigurationId);

    void delete(ProjectSourceConfigurationId projectSourceConfigurationId);

    Collection<ProjectSourceConfiguration> findAll();
}
