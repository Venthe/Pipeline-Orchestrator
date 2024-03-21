package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;

public class EventTypeContext {
    public static EventType ensure(JsonNode root) {
        throw new UnsupportedOperationException();
    }
}
