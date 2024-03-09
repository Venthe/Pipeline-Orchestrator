package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application.ProjectsSourceRepository;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.ProjectSourceConfiguration;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.ProjectSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.utilities.InMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Optional;

@Configuration
public class RepositoryConfiguration {
    @
    ProjectsSourceRepository projectsSourceRepository() {
        return new ProjectsSourceRepository() Bean{
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

            @Override
            public Collection<ProjectSourceConfiguration> findAll() {
                return repository.getAll();
            }
        };
    }
}
