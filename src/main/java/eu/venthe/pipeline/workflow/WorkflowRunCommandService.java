package eu.venthe.pipeline.workflow;

import eu.venthe.pipeline.runner.runner_engine.template.model.dimensions.Dimension;
import eu.venthe.pipeline.workflow.definition.WorkflowDefinition;
import eu.venthe.pipeline.workflow.model.JobRunId;
import eu.venthe.pipeline.workflow.runs.WorkflowRunId;
import eu.venthe.pipeline.project.domain.ProjectId;
import eu.venthe.pipeline.shared_kernel.git.GitRevision;

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
