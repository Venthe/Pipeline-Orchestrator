package eu.venthe.platform.application.config;

import eu.venthe.platform.application.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.platform.project.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.platform.source_configuration.ProjectsSourceConfiguration;
import eu.venthe.platform.source_configuration.SourceConfigurationId;
import org.springframework.stereotype.Component;

@Component
public class SourceConfigurationRepositoryImpl extends InMemoryDomainRepository<ProjectsSourceConfiguration, SourceConfigurationId>
        implements SourceConfigurationRepository {
    @Override
    public boolean exists(SourceConfigurationId id) {
        return findAll().stream().anyMatch(e -> e.getConfigurationId().equals(id));
    }
}
