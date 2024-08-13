package eu.venthe.platform.workflow.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

// TODO: Implement context
@RequiredArgsConstructor
public class WorkflowDefinitionDefaultsContext {
    private WorkflowDefinitionDefaultsContext(JsonNode node) {
    }

    public static Optional<WorkflowDefinitionDefaultsContext> create(JsonNode root) {
        return ContextUtilities.create(root, WorkflowDefinitionDefaultsContext::new);
    }
}
