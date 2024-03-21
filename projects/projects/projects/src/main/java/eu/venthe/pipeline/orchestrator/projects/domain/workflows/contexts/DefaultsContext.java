package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DefaultsContext {
    private final ObjectNode root;

    public static Optional<DefaultsContext> create(ObjectNode root) {
        return ContextUtilities.get(DefaultsContext::new, root.get("defaults"));
    }
}
