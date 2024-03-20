package eu.venthe.pipeline.orchestrator.workflow_executions.api;

public interface WorkflowExecutionQueryService {
    List<WorkflowExecutionDto> getExecutions();

    Optional<WorkflowExecutionDto> getExecutionDetails(WorkflowExecutionId executionId);
}
