package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

// components/deployment.yaml#
public class DeploymentContext {
    public static DeploymentContext ensure(JsonNode deployment) {
        throw new UnsupportedOperationException();
    }

    public static Optional<DeploymentContext> create(JsonNode deployment) {
        throw new UnsupportedOperationException();
    }
}
