package eu.venthe.pipeline.orchestrator.projects_source.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectPlugin;
import eu.venthe.pipeline.orchestrator.projects_source.domain.events.ProjectSourceConfigurationAddedEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectSourceConfigurationFactory {
    private final Map<String, ProjectPlugin> plugins = new HashMap<>();

    @Autowired
    public ProjectSourceConfigurationFactory(Collection<ProjectPlugin> plugins) {
        plugins.forEach(this::registerPlugin);
    }

    private void registerPlugin(ProjectPlugin plugin) {
        log.info("Registering plugin for {}", plugin.getSourceType());
        plugins.put(plugin.getSourceType(), plugin);
    }

    public Pair<ProjectSourceConfiguration, Collection<DomainEvent>> createConfiguration(String id, String sourceType, Map<String, String> properties) {
        ProjectPlugin plugin = getPlugin(sourceType);

        ProjectSourceConfiguration projectSourceConfiguration = new ProjectSourceConfiguration(
                ProjectSourceConfigurationId.of(id),
                sourceType,
                plugin.getProjectProvider(properties),
                plugin.getVersionControlSystem(properties)
        );
        Set<DomainEvent> events = Set.of(new ProjectSourceConfigurationAddedEvent(
                projectSourceConfiguration.getId().getValue(),
                projectSourceConfiguration.getSourceType()
        ));

        return Pair.of(projectSourceConfiguration, events);
    }

    private ProjectPlugin getPlugin(String sourceType) {
        return Optional.ofNullable(plugins.get(sourceType))
                .orElseThrow();
    }
}
