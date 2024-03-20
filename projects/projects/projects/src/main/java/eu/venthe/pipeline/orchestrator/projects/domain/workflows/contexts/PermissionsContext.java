package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PermissionsContext {
    private final ObjectNode root;

    public static Optional<PermissionsContext> create(ObjectNode root) {
        return ContextUtilities.get(PermissionsContext::new, root.get("permissions"));
    }
}
