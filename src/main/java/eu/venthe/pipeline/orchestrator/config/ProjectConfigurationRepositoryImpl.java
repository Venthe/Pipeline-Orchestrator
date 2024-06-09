package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.config.infrastructure.in_memory_repositroy.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.infrastructure.ProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class ProjectConfigurationRepositoryImpl extends InMemoryDomainRepository<Project, ProjectId>
        implements ProjectRepository {
}
