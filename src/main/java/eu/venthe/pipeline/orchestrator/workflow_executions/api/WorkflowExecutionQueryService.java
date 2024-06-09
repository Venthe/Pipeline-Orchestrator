package eu.venthe.pipeline.orchestrator.workflow_executions.api;

import eu.venthe.pipeline.orchestrator.workflow_executions.api.dto.WorkflowExecutionDto;
import eu.venthe.pipeline.orchestrator.workflow_executions.api.model.WorkflowExecutionId;

import java.util.List;
import java.util.Optional;

public interface WorkflowExecutionQueryService {
    List<WorkflowExecutionDto> getExecutions();

    Optional<WorkflowExecutionDto> getExecutionDetails(WorkflowExecutionId executionId);
}
