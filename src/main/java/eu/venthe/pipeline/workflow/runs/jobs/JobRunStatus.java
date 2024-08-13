package eu.venthe.pipeline.workflow.runs.jobs;

public enum JobRunStatus {
    SUCCESS,
    FAILURE,
    CANCELLED,
    REQUESTED,
    RUNNING,
    PENDING;

    // TODO: Add state machine
    public JobRunStatus progress(JobRunStatus newStatus) {
        return newStatus;
    }

    boolean awaitsRun() {
        return this == PENDING;
    }
}
