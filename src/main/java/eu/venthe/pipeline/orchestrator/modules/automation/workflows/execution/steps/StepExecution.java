package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.steps;

import java.time.OffsetDateTime;

public class StepExecution {
    String name;
    OffsetDateTime startedAt;
    OffsetDateTime stoppedAt;
    /**
     * The result of a completed step after continue-on-error is applied.
     */
    Status conclusion;
    /**
     * The result of a completed step before continue-on-error is applied.
     */
    Status outcome;
    StepLogs logs;

    public enum Status {
        SUCCESS,
        FAILURE,
        CANCELLED,
        SKIPPED
    }
}
