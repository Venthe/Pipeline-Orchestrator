package eu.venthe.pipeline.orchestrator.projects_source.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectPlugin;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface ProjectSourceConfigurationFactory {
    void registerPlugin(ProjectPlugin plugin);

    Pair<ProjectSourceConfiguration, Collection<DomainEvent>> createConfiguration(String id, String sourceType, Map<String, String> properties);

    Optional<ProjectPlugin> getPlugin(String sourceType);
}
