package eu.venthe.pipeline.orchestrator.shared_kernel.shared_context;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

// TODO: Implement context
@RequiredArgsConstructor
public class DefaultsContext {
    private final JsonNode root;

    public static Optional<DefaultsContext> create(JsonNode root) {
        return ContextUtilities.create(root, DefaultsContext::new);
    }
}
