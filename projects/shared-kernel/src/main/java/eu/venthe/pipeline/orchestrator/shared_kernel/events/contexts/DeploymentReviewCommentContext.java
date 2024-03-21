package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class DeploymentReviewCommentContext {
    public static Optional<String> create(JsonNode comment) {
        throw new UnsupportedOperationException();
    }
}
