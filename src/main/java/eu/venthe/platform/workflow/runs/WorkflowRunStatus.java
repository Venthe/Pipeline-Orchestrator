package eu.venthe.platform.workflow.runs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkflowRunStatus {
    COMPLETED("completed"),
    ACTION_REQUIRED("action_required"),
    CANCELLED("cancelled"),
    FAILURE("failure"),
    NEUTRAL("neutral"),
    SKIPPED("skipped"),
    STALE("stale"),
    SUCCESS("success"),
    TIMED_OUT("timed_out"),
    IN_PROGRESS("in_progress"),
    QUEUED("queued"),
    REQUESTED("requested"),
    WAITING("waiting"),
    PENDING("pending") ;

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
