package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Sets;
import eu.venthe.pipeline.orchestrator.projects.domain.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class OnInputs {
    private final ObjectNode root;

    public static Optional<OnInputs> create(ObjectNode root) {
        return ContextUtilities.get(OnInputs::new, root.get("inputs"));
    }

    public List<InputDefinition> requiredInputs() {
        if (!root.isObject()) throw new UnsupportedOperationException();

        return root.properties().stream()
                .filter(e -> e.getValue().isObject())
                .map(e -> new InputDefinition(
                        e.getKey(),
                        Optional.ofNullable(e.getValue().get("required"))
                                .map(JsonNode::asBoolean)
                                .orElse(false),
                        ContextUtilities.toTextual(e.getValue().get("type")).orElseThrow()
                ))
                .filter(InputDefinition::getRequired)
                .toList();
    }

    public boolean match(Map<String, String> context) {
        // TODO: Handle type of the input
        Set<String> requiredInputs = requiredInputs().stream().map(InputDefinition::getKey).collect(Collectors.toSet());
        Set<String> inputs = context.keySet();

        boolean result = Sets.difference(requiredInputs, inputs).stream().toList().isEmpty();

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
