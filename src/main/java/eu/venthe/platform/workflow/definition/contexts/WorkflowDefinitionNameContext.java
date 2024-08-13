package eu.venthe.platform.workflow.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class WorkflowDefinitionNameContext {
    public static Optional<String> create(JsonNode root) {
        return ContextUtilities.Text.create(root);
    }
}
