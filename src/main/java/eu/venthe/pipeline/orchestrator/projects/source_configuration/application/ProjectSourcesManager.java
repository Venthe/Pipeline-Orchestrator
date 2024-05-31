/*
package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.domain.infrastructure.ProjectsSourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.projects.shared_kernel.events.ProjectSourceConfigurationAddedEvent;
import eu.venthe.pipeline.orchestrator.projects.shared_kernel.events.ProjectSourceConfigurationRemovedEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.model.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.shared_kernel.Either;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static java.util.Collections.emptyMap;

@RequiredArgsConstructor
public class ProjectSourcesManager {
    private final ProjectsSourceConfigurationRepository sources;

    public Either<Void, Pair<String, List<DomainTrigger>>> register(ProjectSourcePlugin plugin) {
        // TODO: Load configuration from repository/file
        SuppliedProperties properties = new SuppliedProperties(emptyMap());

        ProjectsSourceConfiguration configuration = new ProjectsSourceConfiguration(plugin.instantiate(properties));
        sources.save(configuration);

        List<DomainTrigger> events = List.of(new ProjectSourceConfigurationAddedEvent(configuration.getConfigurationId().id(), plugin.getSourceType().getValue()));
        return Either.success(Pair.of(configuration.getConfigurationId().id(), events));
    }

    public Either<Void, Pair<Boolean, List<DomainTrigger>>> unregister(String configurationId) {
        return Either.success(Pair.of(true, List.of(new ProjectSourceConfigurationRemovedEvent(configurationId))));
    }

    public Either<Void, Pair<String, List<DomainTrigger>>> synchronize(String configurationId) {
        throw new UnsupportedOperationException();
    }

    public Either<Void, Pair<String, List<DomainTrigger>>> synchronizeAll() {
        throw new UnsupportedOperationException();
    }

}
*/
