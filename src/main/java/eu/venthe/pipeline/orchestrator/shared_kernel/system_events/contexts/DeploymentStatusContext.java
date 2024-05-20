package eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

// TODO: Implement context
// TODO: https://docs.github.com/en/webhooks/webhook-events-and-payloads#deployment_status
public class DeploymentStatusContext {
    public static DeploymentStatusContext ensure(final JsonNode deploymentStatus) {
        throw new UnsupportedOperationException();
    }
}
