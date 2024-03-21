package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

// components/review.yaml#
public class PullRequestReviewContext {
    public static PullRequestReviewContext ensure(JsonNode review) {
        throw new UnsupportedOperationException();
    }
}
