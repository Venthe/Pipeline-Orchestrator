package eu.venthe.pipeline.orchestrator.modules.automation.workflows;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive._1.model.query.WorkflowExecutionDto;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobRunId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowCorrelationId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;

import java.util.List;
import java.util.Optional;

public interface WorkflowRunQueryService {
    default List<WorkflowExecutionDto> getExecutions() {
        throw new UnsupportedOperationException();
    }

    default Optional<WorkflowExecutionDto> getExecutionDetails(WorkflowRunId executionId) {
        throw new UnsupportedOperationException();
    }

    default Optional<WorkflowExecutionDto> getExecutionDetails(WorkflowRunId executionId, JobRunId jobRunId) {
        throw new UnsupportedOperationException();
    }

    default Optional<WorkflowExecutionDto> getExecutionDetails(WorkflowCorrelationId workflowCorrelationId) {
        throw new UnsupportedOperationException();
    }
}
