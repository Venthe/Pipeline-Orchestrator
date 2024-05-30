package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.projects.shared_kernel.events.ProjectUpdatedEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
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
    private final ProjectsSourceConfiguration owningConfiguration;

    private Optional<String> description;
    private ProjectStatus status;

    public Collection<DomainTrigger> refreshProject() {
        ProjectDto projectDto = owningConfiguration.getProject(getId().getName()).orElseThrow();
        if (projectDto.getStatus().equals(status) && projectDto.getDescription().equals(description)) {
            return List.of();
        }
        description = projectDto.getDescription();
        status = projectDto.getStatus();

        return List.of(new ProjectUpdatedEvent(getId(), getStatus(), getDescription()));
    }

    private Collection<DomainTrigger> registerTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    private Collection<DomainTrigger> unregisterTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }
}
