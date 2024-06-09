package eu.venthe.pipeline.orchestrator.organizations.domain.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.source_configurations.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface SourceConfigurationRepository extends DomainRepository<ProjectsSourceConfiguration, ProjectsSourceConfigurationId> {
    boolean exists(ProjectsSourceConfigurationId id);

}
