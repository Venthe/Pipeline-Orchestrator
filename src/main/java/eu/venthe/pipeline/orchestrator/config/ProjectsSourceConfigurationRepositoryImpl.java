package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repositroy.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.infrastructure.ProjectsSourceConfigurationRepository;
import org.springframework.stereotype.Component;

@Component
public class ProjectsSourceConfigurationRepositoryImpl extends InMemoryDomainRepository<ProjectsSourceConfiguration, ProjectsSourceConfigurationId>
        implements ProjectsSourceConfigurationRepository {
    @Override
    public boolean exists(ProjectsSourceConfigurationId configurationId) {
        return findAll().stream().anyMatch(e -> e.getConfigurationId().equals(configurationId));
    }
}
