package eu.venthe.pipeline.orchestrator.projects.projects.domain;

import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
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
public class Project implements Aggregate<ProjectId>, ProjectManagement, WorkflowExecution {

    @EqualsAndHashCode.Include
    private final ProjectId id;
    private final ProjectsSourceConfiguration owningConfiguration;

    private Optional<String> description;
    private ProjectStatus status;

    @Override
    public void synchronize() {
        ProjectDto projectDto = owningConfiguration.getProject(getId().getName()).orElseThrow();
        description = projectDto.getDescription();
        status = projectDto.getStatus();
    }

    @Override
    public void setUnavailable() {
        status = ProjectStatus.NOT_AVAILABLE;
    }

    @Override
    public void archive() {
        status = ProjectStatus.ARCHIVED;
    }

    private Collection<DomainTrigger> registerTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    private Collection<DomainTrigger> unregisterTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void handleEvent(SystemEvent event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void executeManualWorkflow(String workflow) {
        throw new UnsupportedOperationException();
    }
}
