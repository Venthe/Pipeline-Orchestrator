package eu.venthe.pipeline.orchestrator.modules.workflow.application;

import eu.venthe.pipeline.orchestrator.modules.workflow._archive.api.dto.WorkflowDto;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;

import java.io.File;
import java.nio.file.Path;

public interface WorkflowExecutionCommandService {
    default void triggerManualWorkflow(WorkflowDto workflow) {
        throw new UnsupportedOperationException();
    }

    default void retriggerWorkflow(WorkflowExecutionId executionId) {
        throw new UnsupportedOperationException();
    }

    default void retriggerJobExecution(WorkflowExecutionId executionId, JobExecutionId jobExecutionId) {
        throw new UnsupportedOperationException();
    }

    default void stopJobExecution(WorkflowExecutionId executionId, JobExecutionId jobExecutionId) {
        throw new UnsupportedOperationException();
    }

    default void stopJobExecutions(WorkflowExecutionId workflowExecutionId) {
        throw new UnsupportedOperationException();
    }

    WorkflowExecutionId triggerManualWorkflow(final ProjectId id, final Revision revision, final Path path);
}
