package eu.venthe.pipeline.orchestrator.workflow_executions.api;

import eu.venthe.pipeline.orchestrator.projects.shared_kernel.ProjectId;
import eu.venthe.pipeline.orchestrator.workflow_executions.api.dto.*;
import eu.venthe.pipeline.orchestrator.workflow_executions.api.model.JobId;
import eu.venthe.pipeline.orchestrator.workflow_executions.api.model.StepExecutionStatus;
import eu.venthe.pipeline.orchestrator.workflow_executions.api.model.StepId;
import eu.venthe.pipeline.orchestrator.workflow_executions.api.model.WorkflowExecutionId;

import java.util.Map;

public interface WorkflowExecutionCommandService {
    void cancelRunningWorkflowExecutions(ProjectId projectId);
    void cancelRunningWorkflowExecution(WorkflowExecutionId executionId);
    WorkflowExecutionId triggerWorkflow(WorkflowDto workflow, EventDto eventDto);
    void retriggerWorkflow(WorkflowExecutionId executionId);
    void updateJobState(WorkflowExecutionId executionId, JobId jobId, Map<StepId, StepExecutionStatus> statuses);
}
