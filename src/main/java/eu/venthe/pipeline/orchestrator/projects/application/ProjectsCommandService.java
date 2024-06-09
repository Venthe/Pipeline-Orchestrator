package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.api.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

import java.io.File;

public interface ProjectsCommandService {

    default void add(ProjectsSourceConfigurationId configurationId, CreateProjectSpecificationDto newProjectDto) {
        throw new UnsupportedOperationException();
    }

    default void synchronize(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void changeStatus(ProjectId projectId, ProjectStatus status) {
        throw new UnsupportedOperationException();
    }

    default ExecutionId executeManualWorkflow(ProjectId projectId, String ref, File workflowFile) {
        throw new UnsupportedOperationException();
    }

    default void handleVersionControlEvent(ProjectId projectId, SystemEvent event) {
        throw new UnsupportedOperationException();
    }

    default void registerManualWorkflow(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void unregisterManualWorkflow(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void registerTrackedBranch(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void unregisterTrackedBranch(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }
}
