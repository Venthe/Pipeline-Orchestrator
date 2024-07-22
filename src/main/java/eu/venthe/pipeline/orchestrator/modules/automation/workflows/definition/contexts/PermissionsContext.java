package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.Permissions;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

// TODO: Implement context
@RequiredArgsConstructor
public class PermissionsContext {
    private PermissionsContext(JsonNode node) {
    }

    // TODO: Use allowed permissions
    public static Optional<PermissionsContext> create(JsonNode root, final Set<Permissions> allowedPermissions) {
        return ContextUtilities.create(root, PermissionsContext::new);
    }

    private static void create(final JsonNode root) {
    }
    // read|write|none
    // read-all
    // write-all
    // permissions: {}
}
