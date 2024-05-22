package eu.venthe.pipeline.orchestrator.projects.domain;

import com.google.common.collect.Sets;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.ProjectFactory;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.plugin.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.plugin.template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class ProjectsSourceConfiguration implements Aggregate<ProjectsSourceConfiguration.Id> {
    // TODO: Make ID stable
    //  Derive ID either from configuration or assign random
    //  Hash of configuration values?
    @EqualsAndHashCode.Include
    @Getter
    private Id id = new Id();
    private final ProjectSourcePlugin.PluginInstance pluginInstance;
    private Collection<Project> knownProjects = new ArrayList<>();

    List<DomainEvent> synchronize() {
        Map<String, ProjectDto> foundProjects = pluginInstance.getProjects().stream()
                .collect(Collectors.toMap(
                        ProjectDto::getId,
                        Function.identity()
                ));

        Set<ProjectId> foundProjectIds = foundProjects.values().stream().map(ProjectDto::getId).map(id -> ProjectId.of(pluginInstance.getSourceType(), id)).collect(Collectors.toSet());
        Set<ProjectId> knownProjectIds = knownProjects.stream().map(Project::getId).collect(Collectors.toSet());

        Set<ProjectId> projectsToPotentiallyUpdate = Sets.intersection(foundProjectIds, knownProjectIds);
        Set<ProjectId> projectsThatNoLongerExistInSource = Sets.difference(knownProjectIds, foundProjectIds);
        Set<Project> newProjects = Sets.difference(foundProjectIds, knownProjectIds).stream().map(p -> ProjectFactory.create(p, foundProjects.get(p).getStatus())).collect(Collectors.toSet());

        List<DomainEvent> events = new ArrayList<>();


        return List.of();
    }

    @Value
    public class Id {
        String value = UUID.randomUUID().toString();
    }
}
