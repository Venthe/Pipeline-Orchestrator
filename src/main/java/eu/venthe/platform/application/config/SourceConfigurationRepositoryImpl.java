package eu.venthe.platform.application.config;

import eu.venthe.platform.application.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.platform.source_configuration.domain.RepositorySourcePluginInstanceAggregate;
import eu.venthe.platform.source_configuration.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;
import org.springframework.stereotype.Component;

@Component
public class SourceConfigurationRepositoryImpl extends InMemoryDomainRepository<RepositorySourcePluginInstanceAggregate, ConfigurationSourceId>
        implements SourceConfigurationRepository {
}
