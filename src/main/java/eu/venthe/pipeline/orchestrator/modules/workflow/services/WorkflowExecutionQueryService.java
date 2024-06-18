package eu.venthe.pipeline.orchestrator.modules.workflow.services;

import eu.venthe.pipeline.orchestrator.modules.workflow._archive.api.dto.WorkflowExecutionDto;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.WorkflowExecutionId;

import java.util.List;
import java.util.Optional;

public interface WorkflowExecutionQueryService {
    List<WorkflowExecutionDto> getExecutions();

    Optional<WorkflowExecutionDto> getExecutionDetails(WorkflowExecutionId executionId);
}
