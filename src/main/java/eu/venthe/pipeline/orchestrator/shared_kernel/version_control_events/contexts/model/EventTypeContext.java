package eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.model;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.model.EventType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventTypeContext {
    public static EventType ensure(final JsonNode root) {
        return ContextUtilities.Text.create(root)
                .flatMap(EventType::of)
                .orElseThrow(() -> new IllegalArgumentException("Event type must be present and matching"));
    }
}
