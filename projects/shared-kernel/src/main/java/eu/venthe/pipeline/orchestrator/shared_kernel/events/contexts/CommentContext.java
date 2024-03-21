package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

// components/comment.yaml#
public class CommentContext {
    public static Optional<String> create(JsonNode comment) {
        throw new UnsupportedOperationException();
    }

    public static String ensure(JsonNode comment) {
        throw new UnsupportedOperationException();
    }
}
