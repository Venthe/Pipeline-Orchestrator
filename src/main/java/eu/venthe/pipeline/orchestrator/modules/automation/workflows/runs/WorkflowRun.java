package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowRunCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobRunId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.Actor;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.TimeService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.TriggeringEntity;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.events.RequestJobRunCommand;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.events.WorkflowRunCreatedEvent;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.JobRuns;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.run_context.JobExecutionContext;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WorkflowRun implements Aggregate<WorkflowRunId> {
    @Getter
    @EqualsAndHashCode.Include
    private final WorkflowRunId id;
    WorkflowRunCommandService.Context context;
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

        Stream<DomainTrigger> a = Stream.of(new WorkflowRunCreatedEvent(run.getProjectId(), run.getId()));
        return Pair.of(
                Stream.concat(a, run.run().stream()).toList(),
                run
        );
    }

    private ProjectId getProjectId() {
        return context.id();
    }

    private List<RequestJobRunCommand> run() {
        return jobs.run().stream().map(e -> new RequestJobRunCommand(context.id(), getId(), new JobRunId(e.jobId(), e.runAttempt()), e.token(), RunnerDimensions.builder().build())).toList();
    }

    /*WorkflowCorrelationId workflowCorrelationId, */
    private WorkflowRun(WorkflowDefinition workflow,
                        WorkflowRunCommandService.Context context,
                        TimeService timeService,
                        TriggeringEntity triggeringEntity) {
        this.context = context;
        this.triggeringEntity = triggeringEntity;
        id = WorkflowRunId.generate();
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

    public JobExecutionContext provideContext(JobRunId executionId) {
        // TODO: Expand upon
        return new JobExecutionContext() {
        };
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
