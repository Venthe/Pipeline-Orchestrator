package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.RunId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.Runner;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.TimeService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.steps.JobCallbackLogEntry;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.steps.StepExecution;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.steps.StepsExecution;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

import static eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.JobRunStatus.*;

@RequiredArgsConstructor
public class JobRunAttempt {
    private final int attemptNumber;
    private final JobRun jobRun;

    private JobRunStatus status = PENDING;
    private ExecutionCallbackToken token;
    private RunId runId;
    private OffsetDateTime startTime;
    private StepsExecution stepsExecution;

    public void request(Runner runner) {
        if (!status.awaitsRun()) {
            throw new IllegalArgumentException();
        }
        token = new ExecutionCallbackToken();
        runId = runner.run(null, token);
        status = status.progress(REQUESTED);
    }

    public void cancel(Runner runner) {
        throw new UnsupportedOperationException();
        // runner.cancel(runId);
        // stepsExecution.cancel();
        // status = status.progress(CANCELLED);
    }

    void run(Set<StepsExecution.StepExecutionSpecification> steps,
             OffsetDateTime startTime,
             ExecutionCallbackToken executionCallbackToken,
             TimeService timeService) {
        validateToken(executionCallbackToken);
        this.startTime = startTime;
        status = status.progress(RUNNING);
        stepsExecution = new StepsExecution(() -> timeService.offset().now(), steps.toArray(StepsExecution.StepExecutionSpecification[]::new));
    }

    void complete(JobRunStatus status, ExecutionCallbackToken executionCallbackToken) {
        validateToken(executionCallbackToken);

        this.status = status.progress(status);

        runId = null;
        token = null;
    }

    void stepStarted(StepExecution.Id stepId, OffsetDateTime startTime, ExecutionCallbackToken executionCallbackToken) {
        validateToken(executionCallbackToken);

        stepsExecution.markStart(stepId, startTime);
    }

    void stepCompleted(StepExecution.Id stepId, final StepExecution.Status outcome, final StepExecution.Status conclusion, ExecutionCallbackToken executionCallbackToken) {
        validateToken(executionCallbackToken);

        stepsExecution.markEnd(stepId, startTime, outcome, conclusion);
    }

    void uploadLog(StepExecution.Id stepId, JobCallbackLogEntry log, ExecutionCallbackToken executionCallbackToken) {
        validateToken(executionCallbackToken);

        stepsExecution.appendLog(stepId, log);
    }

    private void validateToken(final ExecutionCallbackToken token) {
        throw new UnsupportedOperationException();
    }
}
