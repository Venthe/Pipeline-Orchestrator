package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

// components/workflow.yaml#
public class WorkflowContext {
    public static Optional<WorkflowContext> create(JsonNode workflow) {
        throw new UnsupportedOperationException();
    }

    public static WorkflowContext ensure(JsonNode workflow) {
        throw new UnsupportedOperationException();
    }
}
