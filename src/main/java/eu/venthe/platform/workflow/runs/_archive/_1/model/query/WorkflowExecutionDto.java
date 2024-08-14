package eu.venthe.platform.workflow.runs._archive._1.model.query;

import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.workflow.runs.WorkflowRunStatus;

public record WorkflowExecutionDto(WorkflowRunId workflowRunId, WorkflowRunStatus status) {
}
