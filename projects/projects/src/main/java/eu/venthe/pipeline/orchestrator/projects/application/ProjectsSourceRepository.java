package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.configuration.domain.ProjectSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.configuration.domain.ProjectSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface ProjectsSourceRepository extends DomainRepository<ProjectSourceConfiguration, ProjectSourceConfigurationId> {
}
