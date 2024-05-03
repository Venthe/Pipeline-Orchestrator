package eu.venthe.pipeline.orchestrator.projects_source.adapter;

import eu.venthe.pipeline.orchestrator.projects_source.adapter.events.ProjectSourceConfigurationAddedEvent;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.events.ProjectSourceConfigurationRemovedEvent;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.model.ConfigurationProperty;
import eu.venthe.pipeline.orchestrator.shared_kernel.Either;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

@RequiredArgsConstructor
public class AdapterManager {
    private final Map<String, Configuration> sources = new HashMap<>();
    private final ProjectSourcePlugin.Api api;

    public Either<Void, Pair<String, List<DomainEvent>>> register(ProjectSourcePlugin plugin) {
        // TODO: Make ID stable
        //  Derive ID either from configuration or assign random
        //  Hash of configuration values?
        String configurationId = UUID.randomUUID().toString();
        // TODO: Load configuration from repository/file
        Map<String, ConfigurationProperty> properties = Collections.emptyMap();

        sources.put(configurationId, new Configuration(configurationId, plugin.getSourceType(), plugin.instantiate(properties, api)));

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

    @Value
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    static class Configuration {
        @EqualsAndHashCode.Include
        String id;
        @EqualsAndHashCode.Include
        String sourceType;
        ProjectSourcePlugin.Definition definition;
        Collection<KnownProject> knownProjects = new ArrayList<>();
    }
}
