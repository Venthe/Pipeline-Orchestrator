package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PermissionsContext {
    private final JsonNode root;

    public static Optional<PermissionsContext> create(JsonNode root) {
        return ContextUtilities.create(root, PermissionsContext::new);
    }
}
