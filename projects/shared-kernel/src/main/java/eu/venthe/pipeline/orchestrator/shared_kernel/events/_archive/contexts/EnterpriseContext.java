package eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.ContextUtilities;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Deprecated
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class EnterpriseContext {
    private final ObjectNode root;

    public static Optional<EnterpriseContext> create(JsonNode root) {
        return ContextUtilities.get(EnterpriseContext::new, root);
    }
}
