package eu.venthe.pipeline.application.config;

import eu.venthe.pipeline.application.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.pipeline.project.domain.Project;
import eu.venthe.pipeline.project.domain.ProjectId;
import eu.venthe.pipeline.project.domain.infrastructure.ProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class ProjectConfigurationRepositoryImpl extends InMemoryDomainRepository<Project, ProjectId>
        implements ProjectRepository {
}
