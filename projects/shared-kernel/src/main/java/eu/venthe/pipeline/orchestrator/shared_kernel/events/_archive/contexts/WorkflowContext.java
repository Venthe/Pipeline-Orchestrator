package eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Relative path to the workflow file which contains the workflow.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class WorkflowContext {
    public static String ensure(JsonNode root) {
        return ContextUtilities.toTextual(root)
                .orElseThrow(() -> new IllegalArgumentException("workflow must be present"));
    }
}
