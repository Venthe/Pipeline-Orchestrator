package eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.ContextUtilities;

import java.util.Optional;
import java.util.UUID;

public class EventIdContext {
    public static Optional<UUID> create(JsonNode root) {
        return ContextUtilities.toTextual(root)
                .map(UUID::fromString);
    }

    public static UUID ensure(ObjectNode root) {
        return create(root.get("id")).orElseThrow(() -> new IllegalArgumentException("Unique ID for the event must be present"));
    }
}
