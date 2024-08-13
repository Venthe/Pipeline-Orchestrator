package eu.venthe.pipeline.application.config;

import eu.venthe.pipeline.application.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.pipeline.projects.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.pipeline.projects.domain.source_configurations.ProjectsSourceConfiguration;
import eu.venthe.pipeline.projects.domain.source_configurations.SourceConfigurationId;
import org.springframework.stereotype.Component;

@Component
public class SourceConfigurationRepositoryImpl extends InMemoryDomainRepository<ProjectsSourceConfiguration, SourceConfigurationId>
        implements SourceConfigurationRepository {
    @Override
    public boolean exists(SourceConfigurationId id) {
        return findAll().stream().anyMatch(e -> e.getConfigurationId().equals(id));
    }
}
