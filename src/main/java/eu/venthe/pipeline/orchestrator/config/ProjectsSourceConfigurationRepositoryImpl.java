package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.config.infrastructure.in_memory_repositroy.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.infrastructure.ProjectsSourceConfigurationRepository;
import org.springframework.stereotype.Component;

@Component
public class ProjectsSourceConfigurationRepositoryImpl extends InMemoryDomainRepository<ProjectsSourceConfiguration, ProjectsSourceConfigurationId>
        implements ProjectsSourceConfigurationRepository {
    @Override
    public boolean exists(ProjectsSourceConfigurationId id) {
        return findAll().stream().anyMatch(e -> e.getConfigurationId().equals(id));
    }
}
