package eu.venthe.pipeline.workflow;

import eu.venthe.pipeline.workflow.runs._archive._1.model.query.WorkflowExecutionDto;
import eu.venthe.pipeline.workflow.model.JobRunId;
import eu.venthe.pipeline.workflow.runs.WorkflowCorrelationId;
import eu.venthe.pipeline.workflow.runs.WorkflowRunId;

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
