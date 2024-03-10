package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.Envelope;
import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.MessageBroker;
import eu.venthe.pipeline.orchestrator.infrastructure.repository.InMemoryRepository;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application.ProjectsSourceRepository;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.ProjectSourceConfiguration;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.ProjectSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.core.Project;
import eu.venthe.pipeline.orchestrator.projects.core.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.core.ProjectRepository;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
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
        return new ProjectsSourceRepositoryImpl();
    }

    @Bean
    ProjectRepository projectRepository() {
        return new ProjectRepositoryImpl();
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

    private static class ProjectsSourceRepositoryImpl extends DomainRepositoryImpl<ProjectSourceConfiguration, ProjectSourceConfigurationId> implements ProjectsSourceRepository {
    }

    private static class ProjectRepositoryImpl extends DomainRepositoryImpl<Project, ProjectId> implements ProjectRepository {
    }

    private static class DomainRepositoryImpl<AGGREGATE extends Aggregate<AGGREGATE_ID>, AGGREGATE_ID> implements eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository<AGGREGATE, AGGREGATE_ID> {

        private final InMemoryRepository<AGGREGATE, AGGREGATE_ID> repository = new InMemoryRepository<>();

        public void save(AGGREGATE configuration) {
            repository.save(configuration.getId(), configuration);
        }

        public Optional<AGGREGATE> find(AGGREGATE_ID projectSourceConfigurationId) {
            return repository.getById(projectSourceConfigurationId);
        }

        public void delete(AGGREGATE_ID projectSourceConfigurationId) {
            repository.delete(projectSourceConfigurationId);
        }

        public Collection<AGGREGATE> findAll() {
            return repository.getAll();
        }
    }
}
