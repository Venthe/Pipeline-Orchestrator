package eu.venthe.pipeline.orchestrator.workflow_executions.domain.model;

import java.util.UUID;

public record WorkflowExecutionId(String value) {
    public static WorkflowExecutionId generate() {
        return new WorkflowExecutionId(UUID.randomUUID().toString());
    }
}
