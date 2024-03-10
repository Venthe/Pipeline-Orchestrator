package eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application;

import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.ProjectSourceConfiguration;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.ProjectSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface ProjectsSourceRepository extends DomainRepository<ProjectSourceConfiguration, ProjectSourceConfigurationId> {
}
