package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

// TODO: Concurrency
@RequiredArgsConstructor
public class ConcurrencyContext {
    private final JsonNode root;

    public static Optional<ConcurrencyContext> create(JsonNode root) {
        return ContextUtilities.create(root, ConcurrencyContext::new);
    }
}
