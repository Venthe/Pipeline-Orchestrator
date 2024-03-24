package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class EventIdContext {
    public static UUID ensure(final JsonNode root) {
        return ContextUtilities.createText(root)
                .map(UUID::fromString)
                .orElseThrow(() -> new IllegalArgumentException("UUID must be present"));
    }
}
