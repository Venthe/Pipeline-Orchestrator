package eu.venthe.pipeline.orchestrator.projects_source.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectPlugin;
import eu.venthe.pipeline.orchestrator.projects_source.api.events.ProjectSourceConfigurationAddedEvent;
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
public class ProjectSourceConfigurationFactoryImpl implements ProjectSourcePluginQueryService, ProjectSourceConfigurationFactory {
    private final Map<String, ProjectPlugin> plugins = new HashMap<>();

    @Autowired
    public ProjectSourceConfigurationFactoryImpl(Collection<ProjectPlugin> plugins) {
        plugins.forEach(this::registerPlugin);
    }

    @Override
    public void registerPlugin(ProjectPlugin plugin) {
        log.info("Registering plugin for {}", plugin.getSourceType());
        plugins.put(plugin.getSourceType(), plugin);
    }

    @Override
    public Pair<ProjectSourceConfiguration, Collection<DomainEvent>> createConfiguration(String id, String sourceType, Map<String, String> properties) {
        ProjectPlugin plugin = getPlugin(sourceType)
                .orElseThrow();

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

    @Override
    public Optional<ProjectPlugin> getPlugin(String sourceType) {
        return Optional.ofNullable(plugins.get(sourceType));
    }

    @Override
    public Set<String> listSystemTypes() {
        return plugins.keySet();
    }
}
