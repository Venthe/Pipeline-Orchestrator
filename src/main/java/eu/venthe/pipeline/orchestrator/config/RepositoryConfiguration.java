package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repositroy.InMemoryRepository;
import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_message_broker.EnvelopeImpl;
import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_message_broker.MessageBroker;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectRepository;
import eu.venthe.pipeline.orchestrator.projects.shared_kernel.ProjectId;
import eu.venthe.pipeline.orchestrator.projects_source._archive.domain.ProjectSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects_source._archive.domain.ProjectSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects_source._archive.domain.ProjectsSourceRepository;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
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
                broker.publishAll(events.stream().map(EnvelopeImpl::new).collect(Collectors.toSet()));
            }

            @Override
            public void publish(DomainEvent event) {
                broker.publish(new EnvelopeImpl<>(event));
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
