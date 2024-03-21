package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

// components/workflow-run.yaml#
public class WorkflowRunContext {
    public static Optional<WorkflowRunContext> create(JsonNode workflowRun) {
        throw new UnsupportedOperationException();
    }

    public static WorkflowRunContext ensure(JsonNode workflowRun) {
        throw new UnsupportedOperationException();
    }
}
