package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

// components/comment.yaml#
public class PullRequestReviewCommentContext {

    public static PullRequestReviewCommentContext ensure(JsonNode comment) {
        throw new UnsupportedOperationException();
    }
}
