package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.config.infrastructure.in_memory_repositroy.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.source_configurations.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.infrastructure.SourceConfigurationRepository;
import org.springframework.stereotype.Component;

@Component
public class SourceConfigurationRepositoryImpl extends InMemoryDomainRepository<ProjectsSourceConfiguration, ProjectsSourceConfigurationId>
        implements SourceConfigurationRepository {
    @Override
    public boolean exists(ProjectsSourceConfigurationId id) {
        return findAll().stream().anyMatch(e -> e.getConfigurationId().equals(id));
    }
}
