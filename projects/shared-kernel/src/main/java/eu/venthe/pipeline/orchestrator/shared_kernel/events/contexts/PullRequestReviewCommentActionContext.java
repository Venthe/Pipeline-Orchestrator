package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.PullRequestReviewCommentAction;

public class PullRequestReviewCommentActionContext {
    public static PullRequestReviewCommentAction ensure(JsonNode action) {
        throw new UnsupportedOperationException();
    }
}
