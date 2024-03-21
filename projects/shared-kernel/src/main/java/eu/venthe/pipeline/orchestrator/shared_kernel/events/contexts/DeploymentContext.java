package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

// components/deployment.yaml#
public class DeploymentContext {
    public static DeploymentContext ensure(JsonNode deployment) {
        throw new UnsupportedOperationException();
    }
}
