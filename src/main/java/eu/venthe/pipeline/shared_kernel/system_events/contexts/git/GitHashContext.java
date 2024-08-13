package eu.venthe.pipeline.shared_kernel.system_events.contexts.git;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GitHashContext {
    public static String ensure(final JsonNode after) {
        return ContextUtilities.Text.ensure(after);
    }
}
