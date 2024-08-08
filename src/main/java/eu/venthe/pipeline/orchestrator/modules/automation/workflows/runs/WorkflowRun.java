package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.RunnerProvider;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobRunId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.run_context.JobExecutionContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.Actor;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.TimeService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.TriggeringEntity;
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
    WorkflowExecutionCommandService.Context context;
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
                       WorkflowExecutionCommandService.Context context,
                       TimeService timeService,
                       TriggeringEntity triggeringEntity,
                       RunnerProvider runnerProvider) {
        this.context = context;
        this.triggeringEntity = triggeringEntity;
        id = WorkflowRunId.generate();
        startDate = timeService.offset().now();
        status = WorkflowRunStatus.REQUESTED;
        this.workflow = workflow;

        jobs = new JobRuns(this.workflow.getJobs(), this, timeService);
        // FIXME: Remove me
        jobs.start(runnerProvider, context);
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

    public void triggerWorkflow(final RunnerProvider runnerProvider, final WorkflowExecutionCommandService.Context context, final EnvUtil envUtil) {
        jobs.start(runnerProvider, context);
    }

    public void retriggerWorkflow() {
        throw new UnsupportedOperationException();
    }

    public void retriggerJobExecution(JobRunId executionId) {
        throw new UnsupportedOperationException();
    }

    public void stopJobExecution(JobRunId executionId) {
        throw new UnsupportedOperationException();
    }

    public void stopRunningJobExecutions() {
        throw new UnsupportedOperationException();
    }

    public JobExecutionContext provideContext(JobRunId executionId) {
        throw new UnsupportedOperationException();
    }

    public void notifyJobStarted(JobRunId executionId, ZonedDateTime startDate) {
        throw new UnsupportedOperationException();
    }

    public void notifyJobCompleted(JobRunId executionId, ZonedDateTime startDate, Map<String, String> outputs) {
        throw new UnsupportedOperationException();
    }

    public void notifyStepStarted(JobRunId executionId, ZonedDateTime startDate) {
        throw new UnsupportedOperationException();
    }

    public void notifyStepCompleted(JobRunId executionId, ZonedDateTime endDate) {
        throw new UnsupportedOperationException();
    }
}
