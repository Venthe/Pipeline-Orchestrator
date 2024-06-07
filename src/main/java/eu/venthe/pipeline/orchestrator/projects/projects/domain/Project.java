package eu.venthe.pipeline.orchestrator.projects.projects.domain;

import eu.venthe.pipeline.orchestrator.job_executor.application.JobExecutorCommandService;
import eu.venthe.pipeline.orchestrator.job_executor.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectId;
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

import java.io.File;
import java.nio.file.Path;
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

    private final JobExecutorCommandService jobExecutorCommandService;

    private Optional<String> description;
    private ProjectStatus status;

    public void synchronize() {
        ProjectDto projectDto = owningConfiguration.getProject(getId().getName()).orElseThrow();
        description = projectDto.getDescription();
        status = projectDto.getStatus();
    }

    public void makeUnavailable() {
        status = ProjectStatus.NOT_AVAILABLE;
    }

    public void archive() {
        status = ProjectStatus.ARCHIVED;
    }

    public void makePublic() {
        status = ProjectStatus.ARCHIVED;
    }

    public void handleEvent(SystemEvent event) {
        throw new UnsupportedOperationException();
    }

    public void registerManualWorkflow(Path workflowFilename) {
        throw new UnsupportedOperationException();
    }

    public void unregisterManualWorkflow(Path workflowFilename) {
        throw new UnsupportedOperationException();
    }

    public ExecutionId executeManualWorkflow(String ref, File workflowFile) {
        return jobExecutorCommandService.triggerJobExecution(id);
    }

    public void registerTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    public void unregisterTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }
}
