package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.InputUtilities;

import java.util.Map;
import java.util.stream.Collectors;

public class WorkflowDispatchInputsContext {
    private final Map<String, InputUtilities.InputValue<?>> inputs;


    public WorkflowDispatchInputsContext(final JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        inputs = root.properties().stream()
                .map(e -> Map.entry(
                        e.getKey(),
                        InputUtilities.describe(e.getValue())
                ))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    public static WorkflowDispatchInputsContext create(final JsonNode _inputs) {
        return ContextUtilities.ensure(_inputs, WorkflowDispatchInputsContext::new);
    }
}
