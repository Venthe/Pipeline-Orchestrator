package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ConcurrencyContext {
    private final ObjectNode root;

    public static Optional<ConcurrencyContext> create(ObjectNode root) {
        return ContextUtilities.get(ConcurrencyContext::new, root.get("concurrency"));
    }
}
