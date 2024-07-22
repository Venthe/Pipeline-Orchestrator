package eu.venthe.pipeline.orchestrator.modules.automation.workflows;

import eu.venthe.pipeline.orchestrator.modules.automation._archive.api.dto.WorkflowExecutionDto;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.WorkflowCorrelationId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.WorkflowExecutionId;

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
