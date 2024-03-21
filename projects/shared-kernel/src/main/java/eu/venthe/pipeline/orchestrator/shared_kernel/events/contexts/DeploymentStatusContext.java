package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

// TODO: https://docs.github.com/en/webhooks/webhook-events-and-payloads#deployment_status
public class DeploymentStatusContext {
    public static DeploymentStatusContext ensure(JsonNode deploymentStatus) {
        throw new UnsupportedOperationException();
    }
}
