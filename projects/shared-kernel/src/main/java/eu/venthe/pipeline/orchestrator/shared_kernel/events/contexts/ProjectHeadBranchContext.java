package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * The name of the repository's default branch (usually main).
 */
public class ProjectHeadBranchContext {
    public static String ensure(JsonNode masterBranch) {
        throw new UnsupportedOperationException();
    }
}
