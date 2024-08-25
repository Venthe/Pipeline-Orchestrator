package eu.venthe.platform.workflow.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.workflow.data_interpolation.Expression;
import jakarta.annotation.Nullable;
import lombok.Value;

import java.util.Optional;

import static eu.venthe.platform.workflow.data_interpolation.ContextKey.*;

@Value
public class WorkflowDefinitionConcurrencyContext {
    Expression<String> group;
    boolean cancelInProgress;

    public WorkflowDefinitionConcurrencyContext(@Nullable String expression, @Nullable Boolean cancelInProgress) {
        this.cancelInProgress = (cancelInProgress != null) && cancelInProgress;

        group = Expression.<String>builder()
                .value(expression == null ? "default" : expression)
                .allowedKey(SYSTEM)
                .allowedKey(INPUT)
                .allowedKey(VARS)
                .build();
    }

    private WorkflowDefinitionConcurrencyContext(JsonNode node) {
        throw new UnsupportedOperationException();
    }

    public static Optional<WorkflowDefinitionConcurrencyContext> create(JsonNode root) {
        return ContextUtilities.create(root, WorkflowDefinitionConcurrencyContext::new);
    }
}
