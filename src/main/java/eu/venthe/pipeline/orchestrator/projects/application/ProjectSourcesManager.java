package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.domain.infrastructure.ProjectsSourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.projects.events.ProjectSourceConfigurationAddedEvent;
import eu.venthe.pipeline.orchestrator.projects.events.ProjectSourceConfigurationRemovedEvent;
import eu.venthe.pipeline.orchestrator.projects.plugin.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.plugin.template.model.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.shared_kernel.Either;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static java.util.Collections.emptyMap;

@RequiredArgsConstructor
public class ProjectSourcesManager {
    private final ProjectsSourceConfigurationRepository sources;

    public Either<Void, Pair<String, List<DomainEvent>>> register(ProjectSourcePlugin plugin) {
        // TODO: Load configuration from repository/file
        SuppliedProperties properties = new SuppliedProperties(emptyMap());

        ProjectsSourceConfiguration configuration = new ProjectsSourceConfiguration(plugin.instantiate(properties));
        sources.save(configuration);

        List<DomainEvent> events = List.of(new ProjectSourceConfigurationAddedEvent(configuration.getId().getValue(), plugin.getSourceType().getValue()));
        return Either.success(Pair.of(configuration.getId().getValue(), events));
    }

    public Either<Void, Pair<Boolean, List<DomainEvent>>> unregister(String configurationId) {
        return Either.success(Pair.of(true, List.of(new ProjectSourceConfigurationRemovedEvent(configurationId))));
    }

    public Either<Void, Pair<String, List<DomainEvent>>> synchronize(String configurationId) {
        throw new UnsupportedOperationException();
    }

    public Either<Void, Pair<String, List<DomainEvent>>> synchronizeAll() {
        throw new UnsupportedOperationException();
    }

}
