package eu.venthe.pipeline.shared_kernel.system_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.InputUtilities;

import java.util.Map;
import java.util.stream.Collectors;

import static eu.venthe.pipeline.application.utilities.CollectionUtilities.toMap;

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

    public Map<String, String> getSimple() {
        return inputs.entrySet().stream()
                .map(e -> Map.entry(e.getKey(), e.getValue().serialize()))
                .collect(toMap());
    }
}
