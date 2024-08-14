package eu.venthe.platform.application.config;

import eu.venthe.platform.application.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.platform.project.domain.Project;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.project.domain.infrastructure.ProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class ProjectConfigurationRepositoryImpl extends InMemoryDomainRepository<Project, ProjectId>
        implements ProjectRepository {
}
