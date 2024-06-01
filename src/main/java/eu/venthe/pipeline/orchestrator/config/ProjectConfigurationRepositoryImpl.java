package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repositroy.InMemoryDomainRepository;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.infrastructure.ProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class ProjectConfigurationRepositoryImpl extends InMemoryDomainRepository<Project, ProjectId>
        implements ProjectRepository {
}
