package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

/**
 * The type of reference.
 * <p>
 * Example:
 *   - head for refs/heads/main
 *   - tag for refs/tags/main
 * <p>
 * See https://git-scm.com/book/en/v2/Git-Internals-Git-References
 */
@UtilityClass
public class ReferenceTypeContext {
    public static String ensure(final JsonNode root) {
        return ContextUtilities.Text.ensure(root);
    }
}
