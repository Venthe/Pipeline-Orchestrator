package eu.venthe.platform.shared_kernel.system_events.contexts.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class ContextUtilities {
    public static <T> Optional<T> create(final JsonNode root, Function<JsonNode, T> mapper) {
        return Optional.ofNullable(root)
                .filter(Predicate.not(JsonNode::isNull))
                .map(mapper);
    }

    public static JsonNode ensure(final JsonNode root) {
        return ensure(root, Function.identity());
    }

    public static <T> T ensure(final JsonNode root, Function<JsonNode, T> mapper) {
        return ensure(root, mapper, () -> new NoSuchElementException("No value present"));
    }

    public static <T> T ensure(final JsonNode root, Function<JsonNode, T> mapper, Supplier<RuntimeException> supplier) {
        return create(root, mapper).orElseThrow(supplier);
    }

    public static <T> T ensureOptional(JsonNode root, Function<JsonNode, Optional<T>> mapper) {
        return ensure(root, mapper).orElseThrow();
    }

    public static Function<JsonNode, String> toText() {
        return fromTextMapper(UnaryOperator.identity());
    }

    public static <T> Function<JsonNode, T> fromTextMapper(Function<String, T> mapper) {
        return node -> {
            if (!node.isTextual()) {
                throw new IllegalArgumentException();
            }

            return mapper.apply(node.asText());
        };
    }

    public static ObjectNode validateIsObjectNode(JsonNode root) {
        if (!root.isObject()) {
            throw new IllegalArgumentException();
        }

        return (ObjectNode) root;
    }

    public static Function<JsonNode, Boolean> toBoolean() {
        return node -> {
            if (!node.isBoolean()) {
                throw new IllegalArgumentException();
            }

            return node.asBoolean();
        };
    }

    public static Function<JsonNode, Integer> toInteger() {
        return node -> {
            if (!node.isInt()) {
                throw new IllegalArgumentException();
            }

            return node.asInt();
        };
    }

    @UtilityClass
    public static class Collection {
        public static <U, T extends java.util.Collection<U>> T createCollection(JsonNode node, Function<Stream<JsonNode>, T> mapper) {
            return Optional.ofNullable(node)
                    .filter(Predicate.not(JsonNode::isNull))
                    .filter(Predicate.not(JsonNode::isMissingNode))
                    .filter(JsonNode::isArray)
                    .map(e -> mapper.apply(StreamSupport.stream(e.spliterator(), false)))
                    .orElseGet(() -> (T) new ArrayList<U>());
        }
    }

    @UtilityClass
    public static class Text {
        public static Optional<String> create(JsonNode root) {
            return ContextUtilities.create(root, toText());
        }

        public static String ensure(JsonNode root) {
            return create(root).orElseThrow();
        }

        public static String ensure(JsonNode root, Supplier<RuntimeException> exception) {
            return create(root).orElseThrow(exception);
        }
    }

    @UtilityClass
    public static class BooleanTextualNumber {
        public static JsonNode ensure(JsonNode c) {
            if (c.isBoolean() || c.isTextual() || c.isNumber()) {
                return c;
            }

            throw new UnsupportedOperationException();
        }
    }
}
