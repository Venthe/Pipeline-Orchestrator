package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.JobRuns;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WorkflowRun implements Aggregate<WorkflowRunId> {
    @Getter
    @EqualsAndHashCode.Include
    private final WorkflowRunId id;
    @Getter
    private final OffsetDateTime startDate;
    @Getter
    private Optional<OffsetDateTime> endDate;
    @Getter
    private WorkflowRunStatus status;

    private final TriggeringEntity triggeringEntity;
    private final JobRuns jobs;
    private final WorkflowDefinition workflow;

    /*WorkflowCorrelationId workflowCorrelationId, */
    public WorkflowRun(WorkflowDefinition workflow,
                       TimeService timeService,
                       TriggeringEntity triggeringEntity) {
        this.triggeringEntity = triggeringEntity;
        id = WorkflowRunId.generate();
        startDate = timeService.zone().now();
        status = WorkflowRunStatus.REQUESTED;
        this.workflow = workflow;

        jobs = new JobRuns(this.workflow.getJobs());
    }

    public Actor getTriggeringActor() {
        return triggeringEntity.getActor();
    }

    /**
     * What has triggered this workflow (e.g. other workflow) if applicable.
     */
    public WorkflowCorrelationId getTriggerCorrelationId() {
        return triggeringEntity.getCorrelationId();
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
