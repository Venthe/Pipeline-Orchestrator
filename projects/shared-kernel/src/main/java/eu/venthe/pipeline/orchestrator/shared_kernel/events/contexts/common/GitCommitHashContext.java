package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.git.CommitHash;

public class GitCommitHashContext {
    public static CommitHash ensure(JsonNode after) {
        throw new UnsupportedOperationException();
    }
}
