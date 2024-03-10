package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.Envelope;
import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.MessageBroker;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application.ProjectsSourceRepository;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.ProjectSourceConfiguration;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.ProjectSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.infrastructure.repository.InMemoryRepository;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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

            @Override
            public Collection<ProjectSourceConfiguration> findAll() {
                return repository.getAll();
            }
        };
    }

    @Bean
    DomainMessageBroker domainMessageBroker(MessageBroker broker) {
        return new DomainMessageBroker() {
            @Override
            public void publish(Collection<DomainEvent> events) {
                broker.publishAll(events.stream().map(Envelope::new).collect(Collectors.toSet()));
            }

            @Override
            public void publish(DomainEvent event) {
                broker.publish(new Envelope<>(event));
            }
        };
    }
}
