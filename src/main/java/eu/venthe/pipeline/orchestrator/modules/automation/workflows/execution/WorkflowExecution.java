package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution;

import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.WorkflowCorrelationId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.WorkflowExecutionStatus;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.JobExecutionId;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WorkflowExecution implements Aggregate<WorkflowExecutionId> {
    @Getter
    @EqualsAndHashCode.Include
    private final WorkflowExecutionId id;
    private final WorkflowCorrelationId workflowCorrelationId;
    private final ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private WorkflowExecutionStatus status;
    private final WorkflowExecutionJobs jobs;

    private final WorkflowDefinition workflow;

    public WorkflowExecution(WorkflowCorrelationId workflowCorrelationId, WorkflowDefinition workflow) {
        id = WorkflowExecutionId.generate();
        startDate = ZonedDateTime.now();
        status = WorkflowExecutionStatus.REQUESTED;
        this.workflow = workflow;
        this.workflowCorrelationId = workflowCorrelationId;

        jobs = new WorkflowExecutionJobs();
    }

    public void triggerWorkflow() {
        throw new UnsupportedOperationException();
    }

    public void retriggerWorkflow() {
        throw new UnsupportedOperationException();
    }

    public void retriggerJobExecution(JobExecutionId executionId) {
        throw new UnsupportedOperationException();
    }

    public void stopJobExecution(JobExecutionId executionId) {
        throw new UnsupportedOperationException();
    }

    public void stopRunningJobExecutions() {
        throw new UnsupportedOperationException();
    }

    public Object provideJobExecutionContext(JobExecutionId executionId) {
        throw new UnsupportedOperationException();
    }

    public void notifyJobStarted(JobExecutionId executionId, ZonedDateTime startDate) {
        throw new UnsupportedOperationException();
    }

    public void notifyJobCompleted(JobExecutionId executionId, ZonedDateTime startDate, Map<String, String> outputs) {
        throw new UnsupportedOperationException();
    }

    public void notifyStepStarted(JobExecutionId executionId, ZonedDateTime startDate) {
        throw new UnsupportedOperationException();
    }

    public void notifyStepCompleted(JobExecutionId executionId, ZonedDateTime endDate) {
        throw new UnsupportedOperationException();
    }
}
