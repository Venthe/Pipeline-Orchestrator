package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.Dimension;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;

import java.util.Set;

public interface WorkflowExecutionCommandService {

    WorkflowExecutionId triggerWorkflow(final WorkflowDefinition workflowDefinition, final Context context);

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

    record Context(ProjectId id, Revision revision, Set<Dimension> dimensions) {}
}
