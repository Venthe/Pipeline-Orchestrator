package eu.venthe.pipeline.orchestrator.projects_provider.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.RepositoryReader;
import eu.venthe.pipeline.orchestrator.projects_provider.api.events.ProjectDiscoveredEvent;
import eu.venthe.pipeline.orchestrator.projects_provider.api.events.ProjectSourceConfigurationRemovedEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
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
    private final RepositoryReader versionControlSystem;

    @Getter(value = AccessLevel.NONE)
    private final Map<String, KnownProject> knownProjects = new HashMap<>();

    public Collection<DomainEvent> synchronize() {
        log.info("Synchronizing projects in {}", id.getValue());

        Set<DomainEvent> domainEvents = new HashSet<>();

        projectProvider.getProjects().stream()
                .map(projectDto -> new KnownProject(projectDto.getId(), id.getValue(), projectDto.getStatus()))
                .forEach(project -> {
                    if (knownProjects.get(project.getId()) != null) {
                        log.info("Project {} is already registered", project.getId());
                        //return;
                    } else {
                        log.info("Registering project {}", project.getId());
                    }

                    domainEvents.add(new ProjectDiscoveredEvent(project.getId(), id.getValue(), project.getStatus()));
                    knownProjects.put(project.getId(), project);
                });


        return domainEvents;
    }

    public Collection<DomainEvent> delete() {
        return List.of(new ProjectSourceConfigurationRemovedEvent(id.getValue()));
    }

    public <T> T visitor(ProjectSourceVisitor<T> visitor) {
        return visitor.visit(id.getValue(), sourceType);
    }

    public Set<KnownProject> getKnownProjects() {
        return new HashSet<>(knownProjects.values());
    }
}
