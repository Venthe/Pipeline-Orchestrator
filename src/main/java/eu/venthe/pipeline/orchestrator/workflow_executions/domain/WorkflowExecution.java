package eu.venthe.pipeline.orchestrator.workflow_executions.domain;

import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.model.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.model.WorkflowExecutionStatus;
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
    private final ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private WorkflowExecutionStatus status;
    private final WorkflowExecutionJobs jobs;

    private final WorkflowTemplate workflow;

    public WorkflowExecution(WorkflowTemplate workflow) {
        id = WorkflowExecutionId.generate();
        startDate = ZonedDateTime.now();
        status = WorkflowExecutionStatus.REQUESTED;
        this.workflow = workflow;

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
