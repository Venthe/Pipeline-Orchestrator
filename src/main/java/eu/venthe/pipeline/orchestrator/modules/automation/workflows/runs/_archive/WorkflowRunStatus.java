package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive;

public enum WorkflowRunStatus {
    QUEUED,
    IN_PROGRESS,
    WAITING,
    COMPLETED,
    NEUTRAL,
    SUCCESS,
    FAILURE,
    CANCELLED,
    ACTION_REQUIRED,
    TIMED_OUT,
    SKIPPED,
    STALE;
}
