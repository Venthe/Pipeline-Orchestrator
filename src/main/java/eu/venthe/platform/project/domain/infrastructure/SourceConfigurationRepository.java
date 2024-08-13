package eu.venthe.platform.project.domain.infrastructure;

import eu.venthe.platform.data_source_configuration.ProjectsSourceConfiguration;
import eu.venthe.platform.data_source_configuration.SourceConfigurationId;
import eu.venthe.platform.shared_kernel.DomainRepository;

public interface SourceConfigurationRepository extends DomainRepository<ProjectsSourceConfiguration, SourceConfigurationId> {
    boolean exists(SourceConfigurationId id);

}
