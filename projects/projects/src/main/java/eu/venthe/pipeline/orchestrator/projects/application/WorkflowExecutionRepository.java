package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.domain.workflow_executions.WorkflowExecution;

import java.util.Optional;

@Deprecated
public interface WorkflowExecutionRepository {
    void save(WorkflowExecution workflowExecution);

    Optional<Object> get(String workflowExecutionId);
}
