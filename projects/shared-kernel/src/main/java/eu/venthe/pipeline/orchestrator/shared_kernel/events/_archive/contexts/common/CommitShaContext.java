package eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts.common;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * The SHA of the commit
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommitShaContext {
    public static String ensure(JsonNode root) {
        return ContextUtilities.toTextual(root)
                .orElseThrow(() -> new IllegalArgumentException("Sha must be present"));
    }
}
