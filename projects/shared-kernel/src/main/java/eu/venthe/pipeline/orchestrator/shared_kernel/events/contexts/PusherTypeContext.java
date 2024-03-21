package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * The pusher type for the event. Can be either user or a deploy key.
 */
public class PusherTypeContext {
    public static String ensure(JsonNode pusherType) {
        throw new UnsupportedOperationException();
    }
}
