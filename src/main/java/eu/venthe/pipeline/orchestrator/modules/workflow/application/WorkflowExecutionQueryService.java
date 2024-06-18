package eu.venthe.pipeline.orchestrator.modules.workflow.application;

import eu.venthe.pipeline.orchestrator.modules.workflow._archive.api.dto.WorkflowExecutionDto;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.WorkflowCorrelationId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.WorkflowExecutionId;

import java.util.List;
import java.util.Optional;

public interface WorkflowExecutionQueryService {
    default List<WorkflowExecutionDto> getExecutions() {
        throw new UnsupportedOperationException();
    }

    default Optional<WorkflowExecutionDto> getExecutionDetails(WorkflowExecutionId executionId) {
        throw new UnsupportedOperationException();
    }

    default Optional<WorkflowExecutionDto> getExecutionDetails(WorkflowCorrelationId workflowCorrelationId) {
        throw new UnsupportedOperationException();
    }
}
