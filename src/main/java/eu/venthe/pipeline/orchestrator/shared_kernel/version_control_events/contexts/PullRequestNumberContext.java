package eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

/**
 * The pull request number.
 */
@UtilityClass
public class PullRequestNumberContext {
    public static Integer ensure(final JsonNode number) {
        return ContextUtilities.ensure(number, value -> {
            if (!value.isNumber() || !value.isInt()) {
                throw new IllegalArgumentException();
            }

            return value.asInt();
        });
    }
}
