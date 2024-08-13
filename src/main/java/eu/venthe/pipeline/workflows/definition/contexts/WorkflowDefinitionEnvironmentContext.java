package eu.venthe.pipeline.workflows.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

// TODO: Implement context
@RequiredArgsConstructor
public class WorkflowDefinitionEnvironmentContext {
    private WorkflowDefinitionEnvironmentContext(JsonNode node) {
    }

    public static Optional<WorkflowDefinitionEnvironmentContext> create(JsonNode root) {
        return ContextUtilities.create(root, WorkflowDefinitionEnvironmentContext::new);
    }

    public Map<String, String> getProperties() {
        throw new UnsupportedOperationException();
    }
}
