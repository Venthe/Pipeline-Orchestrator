package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GitHashContext {
    public static String ensure(final JsonNode after) {
        return ContextUtilities.Text.ensure(after);
    }
}
