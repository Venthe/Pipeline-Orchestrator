package eu.venthe.platform.project.domain.infrastructure;

import eu.venthe.platform.project.domain.source_configurations.ProjectsSourceConfiguration;
import eu.venthe.platform.project.domain.source_configurations.SourceConfigurationId;
import eu.venthe.platform.shared_kernel.DomainRepository;

public interface SourceConfigurationRepository extends DomainRepository<ProjectsSourceConfiguration, SourceConfigurationId> {
    boolean exists(SourceConfigurationId id);

}
