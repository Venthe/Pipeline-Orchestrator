package eu.venthe.pipeline.orchestrator.workflow_executions.application;

import eu.venthe.pipeline.orchestrator.workflow_executions._archive.api.dto.EventDto;
import eu.venthe.pipeline.orchestrator.workflow_executions._archive.api.dto.WorkflowDto;
import eu.venthe.pipeline.orchestrator.workflow_executions._archive.api.model.JobId;
import eu.venthe.pipeline.orchestrator.workflow_executions._archive.api.model.StepExecutionStatus;
import eu.venthe.pipeline.orchestrator.workflow_executions._archive.api.model.StepId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.model.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectId;

import java.util.Map;

public interface WorkflowExecutionCommandService {
    void cancelRunningWorkflowExecutions(ProjectId projectId);

    void cancelRunningWorkflowExecution(WorkflowExecutionId executionId);

    WorkflowExecutionId triggerWorkflow(WorkflowDto workflow, EventDto eventDto);

    void retriggerWorkflow(WorkflowExecutionId executionId);

    void updateJobState(WorkflowExecutionId executionId, JobId jobId, Map<StepId, StepExecutionStatus> statuses);
}
