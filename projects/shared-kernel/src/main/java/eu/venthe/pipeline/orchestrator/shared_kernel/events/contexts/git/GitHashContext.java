package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.GitHash;

public class GitHashContext {
    public static GitHash ensure(final JsonNode after) {
        throw new UnsupportedOperationException();
    }
}
