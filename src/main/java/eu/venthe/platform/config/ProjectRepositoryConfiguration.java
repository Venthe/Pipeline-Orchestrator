package eu.venthe.platform.config;

import eu.venthe.platform.infrastructure.InMemoryRepository;
import eu.venthe.platform.projects.domain.Project;
import eu.venthe.platform.projects.domain.ProjectCorrelationId;
import eu.venthe.platform.projects.domain.ProjectRepository;
import eu.venthe.platform.projects.domain.SourceConfigurationInternalIdentifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class ProjectRepositoryConfiguration {
    @Bean
    ProjectRepository repositoryRepository() {
        return new ProjectRepository() {
            private final InMemoryRepository<Project.Id, Project> repository = new InMemoryRepository<>();

            @Override
            public void save(Project project) {
                this.repository.save(project.getId(), project);
            }

            @Override
            public Optional<Project> find(SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier,
                                          ProjectCorrelationId projectCorrelationId) {
                return repository.find(new Project.Id(sourceConfigurationInternalIdentifier, projectCorrelationId));
            }
        };
    }
}
