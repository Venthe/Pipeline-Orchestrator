package eu.venthe.pipeline.orchestrator.workflow_executions.application;

import eu.venthe.pipeline.orchestrator.workflow_executions._archive.api.dto.WorkflowExecutionDto;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.WorkflowExecutionId;

import java.util.List;
import java.util.Optional;

public interface WorkflowExecutionQueryService {
    List<WorkflowExecutionDto> getExecutions();

    Optional<WorkflowExecutionDto> getExecutionDetails(WorkflowExecutionId executionId);
}
