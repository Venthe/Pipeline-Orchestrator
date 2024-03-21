package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Optional;

// components/run.yaml#
public class WorkflowJobRunContext {
    public static Optional<WorkflowJobRunContext> create(JsonNode workflowJobRun) {
        throw new UnsupportedOperationException();
    }

    public static List<WorkflowJobRunContext> list(JsonNode workflowJobRuns) {
        throw new UnsupportedOperationException();
    }
}
