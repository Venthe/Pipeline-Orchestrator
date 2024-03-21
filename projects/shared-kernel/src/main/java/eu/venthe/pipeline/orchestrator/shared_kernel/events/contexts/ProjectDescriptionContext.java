package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

/**
 * description: The repository's current description.
 */
public class ProjectDescriptionContext {
    public static Optional<String> create(JsonNode description) {
        throw new UnsupportedOperationException();
    }
}
