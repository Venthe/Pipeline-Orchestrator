package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * The pull request number.
 */
public class PullRequestNumberContext {
    public static Integer ensure(JsonNode number) {
        throw new UnsupportedOperationException();
    }
}
