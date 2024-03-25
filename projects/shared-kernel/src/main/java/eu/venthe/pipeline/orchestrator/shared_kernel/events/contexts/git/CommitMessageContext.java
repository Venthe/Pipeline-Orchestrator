package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;

public class CommitMessageContext {
    public static String ensure(final JsonNode id) {
        return ContextUtilities.Text.ensure(id);
    }
}
