package eu.venthe.pipeline.orchestrator.modules.workflow.services;

import eu.venthe.pipeline.orchestrator.modules.workflow._archive.api.dto.WorkflowDto;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.JobExecutionId;

import java.time.ZonedDateTime;
import java.util.Map;

public interface WorkflowExecutionCommandService {
    void triggerWorkflow(WorkflowDto workflow);
    void retriggerWorkflow(WorkflowExecutionId executionId);
    void retriggerJobExecution(WorkflowExecutionId executionId, JobExecutionId jobExecutionId);
    void stopJobExecution(WorkflowExecutionId executionId, JobExecutionId jobExecutionId);
    void stopJobExecutions(WorkflowExecutionId workflowExecutionId);
    void notifyJobStarted(WorkflowExecutionId workflowExecutionId, JobExecutionId executionId, ZonedDateTime startDate);
    void notifyJobCompleted(WorkflowExecutionId workflowExecutionId, JobExecutionId executionId, ZonedDateTime startDate, Map<String, String> outputs);
    void notifyStepStarted(WorkflowExecutionId workflowExecutionId, JobExecutionId executionId, ZonedDateTime startDate);
    void notifyStepCompleted(WorkflowExecutionId workflowExecutionId, JobExecutionId executionId, ZonedDateTime endDate);
}
