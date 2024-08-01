package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.workflow_execution;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
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
    /**
     * What has triggered this workflow (e.g. other workflow) if applicable.
     */
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

        jobs = new WorkflowExecutionJobs(this.workflow);
    }

    public void triggerWorkflow(final WorkflowExecutionCommandService.Context context, final EnvUtil envUtil) {
        jobs.start(context, envUtil);
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
