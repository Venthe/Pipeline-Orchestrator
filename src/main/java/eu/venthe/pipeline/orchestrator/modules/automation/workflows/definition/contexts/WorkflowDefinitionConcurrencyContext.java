package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;

import java.util.Optional;

// TODO: Implement context
public class WorkflowDefinitionConcurrencyContext {
    private WorkflowDefinitionConcurrencyContext(JsonNode node) {
    }

    public static Optional<WorkflowDefinitionConcurrencyContext> create(JsonNode root) {
        return ContextUtilities.create(root, WorkflowDefinitionConcurrencyContext::new);
    }
}
