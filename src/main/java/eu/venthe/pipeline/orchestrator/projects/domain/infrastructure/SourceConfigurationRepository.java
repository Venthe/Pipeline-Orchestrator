package eu.venthe.pipeline.orchestrator.projects.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface SourceConfigurationRepository extends DomainRepository<ProjectsSourceConfiguration, SourceConfigurationId> {
    boolean exists(SourceConfigurationId id);

}
