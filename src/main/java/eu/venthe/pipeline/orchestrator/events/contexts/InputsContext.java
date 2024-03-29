package eu.venthe.pipeline.orchestrator.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.utilities.ContextUtilities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.google.common.collect.MoreCollectors.toOptional;
import static java.util.Arrays.stream;

/**
 * Inputs to the workflow. Each key represents the name of the input while it's value represents the value of that input.
 */
@Getter
public final class InputsContext {
    private final ObjectNode root;

    private InputsContext(ObjectNode root) {
        this.root = Optional.ofNullable(root)
                .filter(JsonNode::isObject)
                .filter(e -> StreamSupport.stream(e.spliterator(), false)
                        .allMatch(a -> a == null || a.isNull() || a.isBoolean() || a.isNumber() || a.isTextual())
                )
                .orElseThrow();
    }

    public static Optional<InputsContext> create(JsonNode root) {
        return ContextUtilities.get(InputsContext::new, root);
    }

    public static InputsContext ensure(JsonNode root) {
        return create(root).orElseThrow(() -> new IllegalArgumentException("Inputs must be present"));
    }

    public Set<ValueType> getValueTypes() {
        return root.properties().stream()
                .map(e -> {
                    String key = e.getKey();
                    JsonNode node = e.getValue();
                    Boolean hasValue = hasValue(node, JsonNode::isTextual)
                            .or(() -> hasValue(node, JsonNode::isBoolean))
                            .or(() -> hasValue(node, JsonNode::isNumber))
                            .orElse(false);
                    ValueType.Type type = Optional.ofNullable(node).map(InputsContext::mapNodeType).orElseThrow();
                    return new ValueType(key, hasValue, type);
                })
                .collect(Collectors.toSet());
    }

    @NotNull
    private static Optional<Boolean> hasValue(JsonNode node, Predicate<JsonNode> is) {
        return Optional.ofNullable(node)
                .filter(is)
                .filter(Predicate.not(JsonNode::isNull))
                .map(v -> true);
    }

    private static ValueType.Type mapNodeType(JsonNode node) {
        if (node.isNumber()) return ValueType.Type.NUMBER;
        else if (node.isTextual()) return ValueType.Type.STRING;
        else if (node.isBoolean()) return ValueType.Type.BOOLEAN;
        else throw new UnsupportedOperationException();
    }

    @Value
    public static class ValueType {
        String key;
        Boolean hasValue;
        Type type;

        @Getter
        @RequiredArgsConstructor

        public enum Type {
            BOOLEAN("boolean"),
            STRING("string"),
            NUMBER("number");

            private final String value;

            public static Optional<Type> of(String type) {
                if (type == null) {
                    return Optional.empty();
                }

                return stream(values())
                        .filter(t -> t.getValue().equalsIgnoreCase(type.trim()))
                        .collect(toOptional());
            }
        }
    }
}
