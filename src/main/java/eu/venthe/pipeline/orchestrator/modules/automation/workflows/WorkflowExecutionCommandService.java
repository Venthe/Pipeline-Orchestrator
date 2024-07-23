package eu.venthe.pipeline.orchestrator.modules.automation.workflows;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;

import java.nio.file.Path;
import java.util.Map;

public interface WorkflowExecutionCommandService {
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

    default WorkflowExecutionId triggerManualWorkflow(final ProjectId id, final Revision revision, final Path path, final Map<String, String> inputs) {
        throw new UnsupportedOperationException();
    }
}
