package eu.venthe.pipeline.orchestrator.projects_provider.domain;

import eu.venthe.pipeline.orchestrator.projects_source.adapter.ProjectSourceAdapter;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface ProjectSourceConfigurationFactory {
    void registerPlugin(ProjectSourceAdapter plugin);

    Pair<ProjectSourceConfiguration, Collection<DomainEvent>> createConfiguration(String id, String sourceType, Map<String, String> properties);

    Optional<ProjectSourceAdapter> getPlugin(String sourceType);
}
