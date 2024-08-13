package eu.venthe.pipeline.workflows.runs.jobs;

import eu.venthe.pipeline.runners.runner_engine.template.model.RunCallbackToken;
import eu.venthe.pipeline.workflows.runs.dependencies.TimeService;
import eu.venthe.pipeline.workflows.runs.jobs.steps.JobCallbackLogEntry;
import eu.venthe.pipeline.workflows.runs.jobs.steps.StepExecution;
import eu.venthe.pipeline.workflows.runs.jobs.steps.StepsExecution;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Set;

import static eu.venthe.pipeline.workflows.runs.jobs.JobRunStatus.*;

public class JobRunAttempt {
    @Getter
    private final int attemptNumber;
    @Getter
    private RunCallbackToken token;

    private JobRunStatus status = PENDING;
    private OffsetDateTime startTime;
    private StepsExecution stepsExecution;

    JobRunAttempt(int attemptNumber) {
        this.attemptNumber = attemptNumber;
        token = new RunCallbackToken();
        status = status.progress(REQUESTED);
    }

    public void cancel() {
        throw new UnsupportedOperationException();
        // runner.cancel(runId);
        // stepsExecution.cancel();
        // status = status.progress(CANCELLED);
    }

    void run(Set<StepsExecution.StepExecutionSpecification> steps,
             OffsetDateTime startTime,
             RunCallbackToken runCallbackToken,
             TimeService timeService) {
        validateToken(runCallbackToken);
        this.startTime = startTime;
        status = status.progress(RUNNING);
        stepsExecution = new StepsExecution(() -> timeService.offset().now(), steps.toArray(StepsExecution.StepExecutionSpecification[]::new));
    }

    void complete(JobRunStatus status, RunCallbackToken runCallbackToken) {
        validateToken(runCallbackToken);

        this.status = status.progress(status);

        token = null;
    }

    void stepStarted(StepExecution.Id stepId, OffsetDateTime startTime, RunCallbackToken runCallbackToken) {
        validateToken(runCallbackToken);

        stepsExecution.markStart(stepId, startTime);
    }

    void stepCompleted(StepExecution.Id stepId, final StepExecution.Status outcome, final StepExecution.Status conclusion, RunCallbackToken runCallbackToken) {
        validateToken(runCallbackToken);

        stepsExecution.markEnd(stepId, startTime, outcome, conclusion);
    }

    void uploadLog(StepExecution.Id stepId, JobCallbackLogEntry log, RunCallbackToken runCallbackToken) {
        validateToken(runCallbackToken);

        stepsExecution.appendLog(stepId, log);
    }

    private void validateToken(final RunCallbackToken token) {
        throw new UnsupportedOperationException();
    }
}
