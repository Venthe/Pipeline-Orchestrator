package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

// components/workflow-job.yaml#
public class WorkflowJobContext {
    public static WorkflowJobContext ensure(JsonNode workflowJob) {
        throw new UnsupportedOperationException();
    }
}
