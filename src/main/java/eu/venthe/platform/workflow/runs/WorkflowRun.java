package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.project.domain.ProjectId;
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
import eu.venthe.platform.workflow.runs.events.WorkflowRunCreatedEvent;
import eu.venthe.platform.workflow.runs.jobs.JobRunStatus;
import eu.venthe.platform.workflow.runs.jobs.JobRuns;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private Optional<OffsetDateTime> endDate;
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

        return Pair.of(
                Collections.singletonList(new WorkflowRunCreatedEvent(run.getProjectId(), run.getId())),
                run
        );
    }

    private ProjectId getProjectId() {
        return context.id();
    }

    public List<RequestJobRunCommand> run() {
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
        id = WorkflowRunId.generate(context.id());
        startDate = timeService.offset().now();
        status = WorkflowRunStatus.REQUESTED;
        this.workflow = workflow;

        jobs = new JobRuns(this.workflow.getJobs(), this, timeService);
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
        throw new UnsupportedOperationException();
    }

    public List<DomainTrigger> notifyJobCompleted(JobRunId executionId, ZonedDateTime startDate, Map<String, String> outputs) {
        throw new UnsupportedOperationException();
    }

    List<DomainTrigger> progress(final JobRunId id, final StepId key, final JobRunStatus value, ZonedDateTime timeOfChange) {
        throw new UnsupportedOperationException();
    }
}
