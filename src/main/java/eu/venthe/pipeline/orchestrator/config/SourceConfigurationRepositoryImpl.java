package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.projects.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;
import org.springframework.stereotype.Component;

@Component
public class SourceConfigurationRepositoryImpl extends InMemoryDomainRepository<ProjectsSourceConfiguration, SourceConfigurationId>
        implements SourceConfigurationRepository {
    @Override
    public boolean exists(SourceConfigurationId id) {
        return findAll().stream().anyMatch(e -> e.getConfigurationId().equals(id));
    }
}
