package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystem;
import eu.venthe.pipeline.orchestrator.projects.domain.events.ProjectDiscoveredEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.util.Pair;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@RequiredArgsConstructor
@Getter
@Slf4j
public class ProjectSourceConfiguration implements Aggregate<ProjectSourceConfigurationId> {
    private final ProjectSourceConfigurationId id;
    private final String sourceType;
    private final ProjectProvider projectProvider;
    private final VersionControlSystem versionControlSystem;

    private final Set<Project> knownProjects = new HashSet<>();

    public Collection<DomainEvent> synchronize() {
        Set<Pair<ProjectDiscoveredEvent, Project>> collect = projectProvider.getProjects().stream()
                .map(p -> new Project(new Project.Id(id.value(), p.getId().getId())))
                .map(project -> Pair.of(new ProjectDiscoveredEvent(project.getId().id(), project.getId().systemId()), project))
                .collect(Collectors.toSet());

        collect.stream()
                .map(Pair::getSecond)
                .forEach(knownProjects::add);

        return collect.stream()
                .map(Pair::getFirst)
                .collect(Collectors.toSet());
    }

    public Collection<DomainEvent> delete() {
        throw new UnsupportedOperationException();
    }

    public <T> T visitor(ProjectSourceVisitor<T> visitor) {
        return visitor.visit(id.value(), sourceType);
    }
}
