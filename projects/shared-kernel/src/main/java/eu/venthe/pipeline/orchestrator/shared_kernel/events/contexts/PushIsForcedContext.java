package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

public class PushIsForcedContext {
    public static Boolean ensure(JsonNode forced) {
        throw new UnsupportedOperationException();
    }
}
