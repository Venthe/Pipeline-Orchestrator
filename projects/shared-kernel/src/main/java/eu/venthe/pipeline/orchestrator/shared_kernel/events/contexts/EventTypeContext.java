package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventTypeContext {
    public static EventType ensure(final JsonNode root) {
        return ContextUtilities.createText(root)
                .flatMap(EventType::of)
                .orElseThrow(() -> new IllegalArgumentException("Event type must be present and matching"));
    }
}
