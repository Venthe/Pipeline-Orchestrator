package eu.venthe.pipeline.orchestrator.projects_source.domain;

import eu.venthe.pipeline.orchestrator.projects_source.global.events.ProjectSourceConfigurationAddedEvent;
import eu.venthe.pipeline.orchestrator.projects_source.global.events.ProjectSourceConfigurationRemovedEvent;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.SystemApi;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.SuppliedConfigurationProperty;
import eu.venthe.pipeline.orchestrator.shared_kernel.Either;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

@RequiredArgsConstructor
public class ProjectSourcesManager {
    private final ProjectsSourceConfigurationRepository sources;
    private final SystemApi systemApi;

    public Either<Void, Pair<String, List<DomainEvent>>> register(ProjectSourcePlugin plugin) {
        // TODO: Load configuration from repository/file
        Map<String, SuppliedConfigurationProperty> properties = Collections.emptyMap();

        sources.save(new ProjectsSourceConfiguration(configurationId, plugin.getSourceType(), plugin.instantiate(properties, systemApi)));

        return Either.success(Pair.of(configurationId, List.of(new ProjectSourceConfigurationAddedEvent(configurationId, plugin.getSourceType()))));
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
