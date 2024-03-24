package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.GitHash;

public class CommitHashContext {
    public static GitHash ensure(JsonNode after) {
        throw new UnsupportedOperationException();
    }
}
