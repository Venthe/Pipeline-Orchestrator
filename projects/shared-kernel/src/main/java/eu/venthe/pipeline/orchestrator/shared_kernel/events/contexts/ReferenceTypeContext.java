package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;

/**
 * The type of reference.
 * <p>
 * Example:
 *   - head for refs/heads/main
 *   - tag for refs/tags/main
 * <p>
 * See https://git-scm.com/book/en/v2/Git-Internals-Git-References
 */
public class ReferenceTypeContext {
    public static String ensure(JsonNode root) {
        return ContextUtilities.ensureText(root);
    }
}
