package eu.venthe.pipeline.orchestrator.organizations.domain.source_configuration.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.organizations.domain.source_configuration.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface ProjectsSourceConfigurationRepository extends DomainRepository<ProjectsSourceConfiguration, ProjectsSourceConfigurationId> {
    boolean exists(ProjectsSourceConfigurationId id);

}
