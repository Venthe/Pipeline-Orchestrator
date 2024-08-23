package eu.venthe.platform.workflow.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.workflow.data_interpolation.Expression;

import java.util.Optional;

public class WorkflowDefinitionRunNameContext {
    public static Optional<Expression> create(JsonNode root) {
        return ContextUtilities.Text.create(root).map(Expression::new);
    }
}
