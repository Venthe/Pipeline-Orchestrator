package eu.venthe.platform.source_configuration.domain.infrastructure;

import eu.venthe.platform.shared_kernel.DomainRepository;
import eu.venthe.platform.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.platform.source_configuration.domain.SourceConfigurationId;

public interface DataSourceConfigurationRepository extends DomainRepository<ProjectsSourceConfiguration, SourceConfigurationId> {
}
