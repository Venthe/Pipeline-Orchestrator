package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.workflow_execution;

import java.util.UUID;

public record WorkflowExecutionId(String value) {
    public static WorkflowExecutionId generate() {
        return new WorkflowExecutionId(UUID.randomUUID().toString());
    }
}
