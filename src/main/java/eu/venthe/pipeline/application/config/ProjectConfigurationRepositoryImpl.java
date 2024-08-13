package eu.venthe.pipeline.application.config;

import eu.venthe.pipeline.application.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.pipeline.projects.domain.Project;
import eu.venthe.pipeline.projects.domain.ProjectId;
import eu.venthe.pipeline.projects.domain.infrastructure.ProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class ProjectConfigurationRepositoryImpl extends InMemoryDomainRepository<Project, ProjectId>
        implements ProjectRepository {
}
