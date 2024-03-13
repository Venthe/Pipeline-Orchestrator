package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectPlugin;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.projects.domain.events.ProjectSourceConfigurationAddedEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

import static com.google.common.collect.MoreCollectors.onlyElement;

@Service
@RequiredArgsConstructor
public class ProjectSourceConfigurationFactory {
    private final Set<ProjectPlugin> plugins;

    public Pair<ProjectSourceConfiguration, Collection<DomainEvent>> create(ProjectSourceConfigurationDto configurationDto) {
        ProjectPlugin plugin = getPlugin(configurationDto.getSourceType());

        ProjectSourceConfiguration projectSourceConfiguration = new ProjectSourceConfiguration(
                new ProjectSourceConfigurationId(configurationDto.getId()),
                configurationDto.getSourceType(),
                plugin.getProjectProvider(configurationDto.getProperties()),
                plugin.getVersionControlSystem(configurationDto.getProperties())
        );
        Set<DomainEvent> events = Set.of(ProjectSourceConfigurationAddedEvent.builder()
                .sourceId(projectSourceConfiguration.getId().value())
                .sourceType(projectSourceConfiguration.getSourceType())
                .build()
        );

        return Pair.of(projectSourceConfiguration, events);
    }

    private ProjectPlugin getPlugin(String sourceType) {
        return plugins.stream()
                .filter(sourceTypeMatches(sourceType))
                .collect(onlyElement());
    }

    private static Predicate<ProjectPlugin> sourceTypeMatches(String sourceType) {
        return e -> e.getSourceType().compareToIgnoreCase(sourceType) == 0;
    }
}
