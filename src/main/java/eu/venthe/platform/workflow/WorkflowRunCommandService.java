package eu.venthe.platform.workflow;

import eu.venthe.platform.repository.domain.RepositoryId;
import eu.venthe.platform.runner.runner_engine.template.model.dimensions.Dimension;
import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.workflow.definition.WorkflowDefinition;
import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.workflow.runs.dependencies.TriggeringEntity;

import java.nio.file.Path;
import java.util.Set;

public interface WorkflowRunCommandService {
    WorkflowRunId triggerWorkflowDispatch(final RepositoryId id, final SimpleRevision simpleRevision, final Path workflowPath);

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
    WorkflowRunId triggerWorkflow(WorkflowDefinition definition, Context context, TriggeringEntity triggeringEntity);

    record Context(
            RepositoryId id,
            SimpleRevision revision,
            Set<Dimension> dimensions
    ) {
    }
}
