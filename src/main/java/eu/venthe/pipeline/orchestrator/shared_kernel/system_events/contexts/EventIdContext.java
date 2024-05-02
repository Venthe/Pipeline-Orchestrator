package eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class EventIdContext {
    public static UUID ensure(final JsonNode root) {
        return ContextUtilities.Text.create(root)
                .map(UUID::fromString)
                .orElseThrow(() -> new IllegalArgumentException("UUID must be present"));
    }
}
