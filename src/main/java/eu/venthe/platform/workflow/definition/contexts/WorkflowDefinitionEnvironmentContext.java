package eu.venthe.platform.workflow.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

// TODO: Implement context
//  github, secrets, inputs, vars
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
