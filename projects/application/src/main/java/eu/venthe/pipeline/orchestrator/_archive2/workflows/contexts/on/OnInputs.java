package eu.venthe.pipeline.orchestrator._archive2.workflows.contexts.on;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator._archive2.events.contexts.common.InputsContext;
import eu.venthe.pipeline.orchestrator._archive2.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class OnInputs {
    private final ObjectNode root;

    public static Optional<OnInputs> create(ObjectNode root) {
        return ContextUtilities.get(OnInputs::new, root.get("inputs"));
    }

    public List<InputDefinition> requiredInputs() {
        if(!root.isObject()) throw new UnsupportedOperationException();

        return root.properties().stream()
                .filter(e ->e.getValue().isObject())
                .map(e-> new InputDefinition(
                        e.getKey(),
                        Optional.ofNullable(e.getValue().get("required"))
                                .map(JsonNode::asBoolean)
                                .orElse(false),
                        ContextUtilities.toTextual(e.getValue().get("type")).orElseThrow()
                ))
                .filter(InputDefinition::getRequired)
                .toList();
    }

    public boolean match(InputsContext context) {
        // TODO: Handle type of the input
        Set<InputsContext.ValueType> inputs = context.getValueTypes();
        boolean result = requiredInputs().stream()
                .allMatch(inputDefinition -> inputs.stream().anyMatch(input -> input.getType().getValue().equalsIgnoreCase(inputDefinition.getType()) && input.getHasValue()));
        log.info("matching inputs - {}", result);
        return result;
    }

    @Value
    public static class InputDefinition {
        String key;
        Boolean required;
        String type;
    }
}
