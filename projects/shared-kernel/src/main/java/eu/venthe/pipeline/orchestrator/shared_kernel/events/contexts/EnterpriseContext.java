package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

public class EnterpriseContext {
    public static Optional<EnterpriseContext> create(JsonNode root) {
        throw new UnsupportedOperationException();
    }
}
