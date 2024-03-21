package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Sets;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.ContextUtilities;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
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
        Map<String, String> requiredInputs = requiredInputs().stream()
                .collect(Collectors.toMap(
                        InputDefinition::getKey,
                        InputDefinition::getType
                ));
        Map<String, String> inputs = context.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> mapInputToType(e.getValue())
                ));

        boolean matchesRequired = Sets.difference(
                new HashSet<>(requiredInputs.keySet()),
                new HashSet<>(inputs.keySet())
        ).isEmpty();

        boolean matchesTypes = requiredInputs.entrySet().stream()
                .allMatch(e -> Optional.ofNullable(inputs.get(e.getKey())).map(v -> v.equalsIgnoreCase(e.getValue())).orElse(false));

        log.info("matching input required - {}, matching input types - {}", matchesRequired, matchesTypes);
        return matchesRequired && matchesTypes;
    }

    private static String mapInputToType(String input) {
        // Define regular expressions for boolean, number, and string
        String booleanRegex = "(?i)true|false";
        String numberRegex = "-?\\d+(\\.\\d+)?";
        String stringRegex = "^[a-zA-Z]+$";

        // Check for boolean
        if (Pattern.matches(booleanRegex, input)) {
            return "boolean";
        }
        // Check for number
        else if (Pattern.matches(numberRegex, input)) {
            return "number";
        }
        // Check for string
        else if (Pattern.matches(stringRegex, input)) {
            return "string";
        } else {
            return "unknown";
        }
    }

    @Value
    public static class InputDefinition {
        String key;
        Boolean required;
        String type;
    }
}
