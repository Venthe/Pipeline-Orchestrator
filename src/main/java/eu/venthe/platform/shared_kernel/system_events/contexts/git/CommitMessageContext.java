package eu.venthe.platform.shared_kernel.system_events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;

public class CommitMessageContext {
    public static String ensure(final JsonNode id) {
        return ContextUtilities.Text.ensure(id);
    }
}
