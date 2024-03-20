package eu.venthe.pipeline.orchestrator.workflow_executions.api;

import eu.venthe.pipeline.orchestrator.projects.shared_kernel.ProjectId;

public interface WorkflowExecutionCommandService {
    void cancelRunningWorkflowExecutions(ProjectId projectId);
    void cancelRunningWorkflowExecution(WorkflowExecutionId executionId);
    WorkflowExecutionId triggerWorkflow(WorkflowDto workflow, EventDto eventDto);
    void retriggerWorkflow(WorkflowExecutionId executionId);
    void updateJobState(WorkflowExecutionId executionId, JobId jobId, Map<StepId, StepExecutionStatus> statuses);
}
