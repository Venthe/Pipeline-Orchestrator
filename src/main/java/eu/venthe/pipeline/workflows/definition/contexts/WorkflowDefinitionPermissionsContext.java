package eu.venthe.pipeline.workflows.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.workflows.model.Permissions;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

// TODO: Implement context
@RequiredArgsConstructor
public class WorkflowDefinitionPermissionsContext {
    private WorkflowDefinitionPermissionsContext(JsonNode node) {
    }

    // TODO: Use allowed permissions
    public static Optional<WorkflowDefinitionPermissionsContext> create(JsonNode root, final Set<Permissions> allowedPermissions) {
        return ContextUtilities.create(root, WorkflowDefinitionPermissionsContext::new);
    }
    // read|write|none
    // read-all
    // write-all
    // permissions: {}
}
