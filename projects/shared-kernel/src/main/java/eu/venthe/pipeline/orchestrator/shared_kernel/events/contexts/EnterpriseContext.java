package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

// components/enterprise.yaml#
public class EnterpriseContext {
    public static Optional<EnterpriseContext> create(JsonNode root) {
        throw new UnsupportedOperationException();
    }
}
