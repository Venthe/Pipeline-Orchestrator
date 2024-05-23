package eu.venthe.pipeline.orchestrator.projects.domain.projects;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.projects.events.ProjectUpdatedEvent;
import eu.venthe.pipeline.orchestrator.projects.plugin.template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Project implements Aggregate<ProjectId> {

    @EqualsAndHashCode.Include
    private final ProjectId id;
    private Optional<String> description;
    private ProjectStatus status;

    private final ProjectsSourceConfiguration owningConfiguration;

    public Collection<DomainEvent> refreshProject() {
        ProjectDto projectDto = owningConfiguration.getProject(getId()).orElseThrow();
        if (projectDto.getStatus().equals(status) && projectDto.getDescription().equals(description)) {
            return List.of();
        }
        description = projectDto.getDescription();
        status = projectDto.getStatus();

        return List.of(new ProjectUpdatedEvent(getId(), getStatus(), getDescription()));
    }

    private Collection<DomainEvent> registerTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    private Collection<DomainEvent> unregisterTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }
}
