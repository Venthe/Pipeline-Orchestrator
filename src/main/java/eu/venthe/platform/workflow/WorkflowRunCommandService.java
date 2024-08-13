package eu.venthe.platform.workflow;

import eu.venthe.platform.runner.runner_engine.template.model.dimensions.Dimension;
import eu.venthe.platform.workflow.definition.WorkflowDefinition;
import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.shared_kernel.git.GitRevision;

import java.nio.file.Path;
import java.util.Set;

public interface WorkflowRunCommandService {
    WorkflowRunId triggerWorkflowDispatch(final ProjectId id, final GitRevision revision, final Path workflowPath);

    default void retriggerWorkflow(WorkflowRunId executionId) {
        throw new UnsupportedOperationException();
    }

    default void retriggerJobExecution(WorkflowRunId executionId, JobRunId jobRunId) {
        throw new UnsupportedOperationException();
    }

    default void stopJobExecution(WorkflowRunId executionId, JobRunId jobRunId) {
        throw new UnsupportedOperationException();
    }

    default void stopJobExecutions(WorkflowRunId workflowRunId) {
        throw new UnsupportedOperationException();
    }

    // Low level API
    default WorkflowRunId triggerWorkflow(WorkflowDefinition definition, Context context) {
        throw new UnsupportedOperationException();
    }

    record Context(ProjectId id, GitRevision revision, Set<Dimension> dimensions) {}
}
