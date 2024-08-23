package eu.venthe.platform.workflow.runs;

import lombok.NonNull;

import java.util.UUID;

public record WorkflowRunId(@NonNull String value) {
    public static WorkflowRunId generate() {
        return new WorkflowRunId(UUID.randomUUID().toString());
    }
}
