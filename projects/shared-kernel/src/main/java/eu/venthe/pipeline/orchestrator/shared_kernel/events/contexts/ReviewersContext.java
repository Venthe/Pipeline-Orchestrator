package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

// components/reviewers.yaml#
public class ReviewersContext {
    public static List<ReviewersContext> list(JsonNode reviewers) {
        throw new UnsupportedOperationException();
    }
}
