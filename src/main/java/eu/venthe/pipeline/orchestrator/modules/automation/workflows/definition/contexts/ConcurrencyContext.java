package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;

import java.util.Optional;

// TODO: Implement context
public class ConcurrencyContext {
    private ConcurrencyContext(JsonNode node) {
    }

    public static Optional<ConcurrencyContext> create(JsonNode root) {
        return ContextUtilities.create(root, ConcurrencyContext::new);
    }
}
