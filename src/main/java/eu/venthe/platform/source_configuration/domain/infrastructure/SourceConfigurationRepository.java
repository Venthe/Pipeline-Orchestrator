package eu.venthe.platform.source_configuration.domain.infrastructure;

import eu.venthe.platform.shared_kernel.DomainRepository;
import eu.venthe.platform.source_configuration.domain.ProjectSourcePluginInstanceAggregate;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;

public interface SourceConfigurationRepository extends DomainRepository<ProjectSourcePluginInstanceAggregate, ConfigurationSourceId> {
}
