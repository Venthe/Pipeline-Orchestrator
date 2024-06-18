package eu.venthe.pipeline.orchestrator.modules.workflow.domain.model;

import java.util.UUID;

public record WorkflowExecutionId(String value) {
    public static WorkflowExecutionId generate() {
        return new WorkflowExecutionId(UUID.randomUUID().toString());
    }
}
