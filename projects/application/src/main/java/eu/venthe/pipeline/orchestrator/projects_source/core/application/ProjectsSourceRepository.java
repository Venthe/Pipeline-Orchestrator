package eu.venthe.pipeline.orchestrator.projects_source.core.application;

import eu.venthe.pipeline.orchestrator.projects_source.core.domain.ProjectSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects_source.core.domain.ProjectSourceConfigurationId;

import java.util.Optional;

public interface ProjectsSourceRepository {
    void save(ProjectSourceConfiguration configuration);

    Optional<ProjectSourceConfiguration> find(ProjectSourceConfigurationId projectSourceConfigurationId);

    void delete(ProjectSourceConfigurationId projectSourceConfigurationId);
}
