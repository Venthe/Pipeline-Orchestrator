package eu.venthe.platform.workflow.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.workflow.data_interpolation.Expression;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static eu.venthe.platform.workflow.data_interpolation.ContextKey.*;

@UtilityClass
public class WorkflowDefinitionRunNameContext {
    public static Optional<Expression<String>> create(JsonNode root) {
        return ContextUtilities.Text.create(root).map(value -> Expression.<String>builder()
                .value(value)
                .allowedKey(SYSTEM)
                .allowedKey(INPUT)
                .allowedKey(VARS)
                .build());
    }
}
