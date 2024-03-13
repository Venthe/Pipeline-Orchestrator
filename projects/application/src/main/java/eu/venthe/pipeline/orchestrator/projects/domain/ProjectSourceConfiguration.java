package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectDto;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystem;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
        Collection<ProjectDto> projects = projectProvider.getProjects();
        // FIXME:
        log.info("{}", projects);
        throw new UnsupportedOperationException();
    }

    public Collection<DomainEvent> delete() {
        throw new UnsupportedOperationException();
    }

    public <T> T visitor(ProjectSourceVisitor<T> visitor) {
        // FIXME:
        return visitor.visit(id.value(), sourceType, new HashSet<>());
    }
}
