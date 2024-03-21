package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

// components/pull-request.yaml#
public class PullRequestContext {
    public static PullRequestContext ensure(JsonNode pullRequest) {
        throw new UnsupportedOperationException();
    }
}
