package eu.venthe.pipeline.orchestrator.organizations.domain.projects;

import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import eu.venthe.pipeline.orchestrator.workflow_executions.application.JobExecutorCommandService;
import eu.venthe.pipeline.orchestrator.workflow_executions.application.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.model.JobExecutionId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.nio.file.Path;
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

    // TODO: Move to plugin
    public void registerManualWorkflow(Path workflowFilename) {
        throw new UnsupportedOperationException();
    }

    // TODO: Move to plugin
    public void unregisterManualWorkflow(Path workflowFilename) {
        throw new UnsupportedOperationException();
    }

    // TODO: Move to plugin
    public JobExecutionId executeManualWorkflow(String ref, File workflowFile) {
        // return jobExecutorCommandService.triggerJobExecution(id);
        throw new UnsupportedOperationException();
    }

    /**
     * Register ref to be presented
     *
     * @param ref
     */
    public void registerTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    public void unregisterTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }
}
