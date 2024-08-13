package eu.venthe.pipeline.workflow.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;

import java.util.Optional;

public class WorkflowDefinitionRunNameContext {
    public static Optional<String> create(JsonNode root) {
        return ContextUtilities.Text.create(root);
    }
}
