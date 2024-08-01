package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

import lombok.NonNull;

import java.util.UUID;

public record WorkflowRunId(@NonNull String value) {
    public static WorkflowRunId generate() {
        return new WorkflowRunId(UUID.randomUUID().toString());
    }
}
