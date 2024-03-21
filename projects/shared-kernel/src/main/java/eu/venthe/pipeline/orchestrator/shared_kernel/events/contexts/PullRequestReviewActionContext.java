package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.PullRequestReviewAction;

public class PullRequestReviewActionContext {
    public static PullRequestReviewAction ensure(JsonNode action) {
        throw new UnsupportedOperationException();
    }
}
