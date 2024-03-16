package eu.venthe.pipeline.orchestrator.projects_source.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystemProvider;
import eu.venthe.pipeline.orchestrator.projects_source.api.events.ProjectDiscoveredEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@RequiredArgsConstructor
@Getter
@Slf4j
public class ProjectSourceConfiguration implements Aggregate<ProjectSourceConfigurationId> {
    private final ProjectSourceConfigurationId id;
    private final String sourceType;
    private final ProjectProvider projectProvider;
    private final VersionControlSystemProvider versionControlSystem;

    @Getter(value = AccessLevel.NONE)
    private final Map<String, KnownProject> knownProjects = new HashMap<>();

    public Collection<DomainEvent> synchronize() {
        log.info("Synchronizing projects in {}", id.getValue());

        Set<DomainEvent> domainEvents = new HashSet<>();

        projectProvider.getProjects().stream()
                .map(projectDto -> new KnownProject(projectDto.getId(), id.getValue()))
                .forEach(project -> {
                    if (knownProjects.get(project.getId()) != null) {
                        log.info("Project {} is already registered", project.getId());
                        return;
                    }

                    log.info("Registering project {}", project.getId());
                    domainEvents.add(new ProjectDiscoveredEvent(project.getId(), id.getValue()));
                    knownProjects.put(project.getId(), project);
                });


        return domainEvents;
    }

    public Collection<DomainEvent> delete() {
        throw new UnsupportedOperationException();
    }

    public <T> T visitor(ProjectSourceVisitor<T> visitor) {
        return visitor.visit(id.getValue(), sourceType);
    }

    public Set<KnownProject> getKnownProjects() {
        return new HashSet<>(knownProjects.values());
    }
}
