package eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts.definitions.InstallationLite;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class InstallationContext implements InstallationLite {
    private final ObjectNode root;

    public static Optional<InstallationLite> create(JsonNode root) {
        return ContextUtilities.get(InstallationContext::new, root);
    }

    @Override
    public Integer getId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNodeId() {
        throw new UnsupportedOperationException();
    }
}
