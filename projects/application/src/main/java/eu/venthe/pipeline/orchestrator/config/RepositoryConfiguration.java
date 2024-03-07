package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.projects_source.core.application.ProjectsSourceRepository;
import eu.venthe.pipeline.orchestrator.projects_source.core.domain.ProjectSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects_source.core.domain.ProjectSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.utilities.InMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class RepositoryConfiguration {
    @Bean
    ProjectsSourceRepository projectsSourceRepository() {
        return new ProjectsSourceRepository() {
            private final InMemoryRepository<ProjectSourceConfiguration, ProjectSourceConfigurationId> repository = new InMemoryRepository<>();

            @Override
            public void save(ProjectSourceConfiguration configuration) {
                repository.save(configuration.getId(), configuration);
            }

            @Override
            public Optional<ProjectSourceConfiguration> find(ProjectSourceConfigurationId projectSourceConfigurationId) {
                return repository.getById(projectSourceConfigurationId);
            }

            @Override
            public void delete(ProjectSourceConfigurationId projectSourceConfigurationId) {
                repository.delete(projectSourceConfigurationId);
            }
        };
    }
}
