package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.shared_kernel.Aggregate;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.workflow.WorkflowRunCommandService;
import eu.venthe.platform.workflow.definition.WorkflowDefinition;
import eu.venthe.platform.workflow.definition._archive.steps.StepId;
import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.workflow.runs.dependencies.Actor;
import eu.venthe.platform.workflow.runs.dependencies.TimeService;
import eu.venthe.platform.workflow.runs.dependencies.TriggeringEntity;
import eu.venthe.platform.workflow.runs.events.RequestJobRunCommand;
import eu.venthe.platform.workflow.runs.jobs.JobRunStatus;
import eu.venthe.platform.workflow.runs.jobs.JobRuns;
import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WorkflowRun implements Aggregate<WorkflowRunId> {
    @Getter
    @EqualsAndHashCode.Include
    private final WorkflowRunId id;
    private final WorkflowRunCommandService.Context context;
    @Getter
    private final OffsetDateTime startDate;
    @Getter
    @Nullable
    private OffsetDateTime endDate;
    @Getter
    private WorkflowRunStatus status;

    private final TriggeringEntity triggeringEntity;
    private final JobRuns jobs;
    private final WorkflowDefinition workflow;

    public static Pair<List<DomainTrigger>, WorkflowRun> crate(WorkflowDefinition workflow,
                                                               WorkflowRunCommandService.Context context,
                                                               TimeService timeService,
                                                               TriggeringEntity triggeringEntity) {
        var run = new WorkflowRun(workflow, context, timeService, triggeringEntity);

        throw new UnsupportedOperationException();
        /*return Pair.of(
                Collections.singletonList(new WorkflowRunCreatedEvent(run.getRepositoryId(), run.getId())),
                run
        );*/
    }

    public static Pair<List<DomainTrigger>, WorkflowRun> crate(WorkflowData workflow,
                                                               EventData eventData,
                                                               TimeService service) {
        throw new UnsupportedOperationException();
    }

    public List<RequestJobRunCommand> trigger() {
        return jobs.run().stream()
                .map(e -> new RequestJobRunCommand(context.id(), getId(), new JobRunId(e.jobId(), e.runAttempt())))
                .toList();
    }

    /*WorkflowCorrelationId workflowCorrelationId, */
    private WorkflowRun(WorkflowDefinition workflow,
                        WorkflowRunCommandService.Context context,
                        TimeService timeService,
                        TriggeringEntity triggeringEntity) {
        this.context = context;
        this.triggeringEntity = triggeringEntity;
        throw new UnsupportedOperationException();
        // id = WorkflowRunId.generate(context.id());
        // startDate = timeService.offset().now();
        // status = WorkflowRunStatus.REQUESTED;
        // this.workflow = workflow;
        //
        // jobs = new JobRuns(this.workflow.getJobs(), timeService);
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

    public List<DomainTrigger> notifyJobStarted(JobRunId executionId, ZonedDateTime startDate) {
        return jobs.notifyJobStarted(executionId, startDate);
    }

    public List<DomainTrigger> notifyJobCompleted(JobRunId executionId, ZonedDateTime startDate, Map<String, String> outputs) {
        return jobs.notifyJobCompleted(executionId, startDate, outputs);
    }

    List<DomainTrigger> progress(JobRunId id, StepId key, JobRunStatus value, ZonedDateTime timeOfChange) {
        return jobs.progress(id, key, value, timeOfChange);
    }

    public String getRunName() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }
}
