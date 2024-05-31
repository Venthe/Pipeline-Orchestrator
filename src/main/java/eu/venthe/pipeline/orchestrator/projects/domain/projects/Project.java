package eu.venthe.pipeline.orchestrator.projects.domain.projects;

import eu.venthe.pipeline.orchestrator.projects.domain.source_configuration.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
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

    public void update() {
        ProjectDto projectDto = owningConfiguration.getProject(getId().getName()).orElseThrow();
        description = projectDto.getDescription();
        status = projectDto.getStatus();
    }

    public void setUnavailable() {
        status = ProjectStatus.NOT_AVAILABLE;
    }

    public void archive() {
        status = ProjectStatus.ARCHIVED;
    }

    private Collection<DomainTrigger> registerTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    private Collection<DomainTrigger> unregisterTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }
}
