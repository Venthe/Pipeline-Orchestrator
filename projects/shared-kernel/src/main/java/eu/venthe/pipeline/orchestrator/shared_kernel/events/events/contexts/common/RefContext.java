package eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.common;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.projects.domain.utilities.ContextUtilities;

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
