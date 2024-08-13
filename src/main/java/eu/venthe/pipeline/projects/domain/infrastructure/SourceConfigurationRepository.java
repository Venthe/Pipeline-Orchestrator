package eu.venthe.pipeline.projects.domain.infrastructure;

import eu.venthe.pipeline.projects.domain.source_configurations.ProjectsSourceConfiguration;
import eu.venthe.pipeline.projects.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.shared_kernel.DomainRepository;

public interface SourceConfigurationRepository extends DomainRepository<ProjectsSourceConfiguration, SourceConfigurationId> {
    boolean exists(SourceConfigurationId id);

}