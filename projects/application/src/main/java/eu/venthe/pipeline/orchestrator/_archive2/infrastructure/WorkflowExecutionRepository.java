package eu.venthe.pipeline.orchestrator._archive2.infrastructure;

import eu.venthe.pipeline.orchestrator._archive2.workflow_executions.WorkflowExecution;

import java.util.Optional;

public interface WorkflowExecutionRepository {
    void save(WorkflowExecution workflowExecution);

    Optional<WorkflowExecution> get(String workflowExecutionId);
}
