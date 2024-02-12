package eu.venthe.pipeline.orchestrator.events.contexts.common;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.utilities.ContextUtilities;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * The full ref. E.g. refs/heads/main or refs/tags/v1.0
 */
public final class RefContext {
    public static String ensure(JsonNode root) {
        return create(root)
                .orElseThrow(() -> new IllegalArgumentException("ref must be present"));
    }

    public static Optional<String> create(JsonNode root) {
        return ContextUtilities.toTextual(root);
    }
}
