package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.StreamSupport;

@UtilityClass
public class ContextUtilities {
    @Deprecated
    public static <T> Optional<T> get(Function<ObjectNode, T> creator, JsonNode root) {
        return Optional.ofNullable(root)
                .filter(Predicate.not(JsonNode::isNull))
                .map(node -> creator.apply((ObjectNode) node));
    }

    public static <T extends Collection<String>> Optional<T> createStringCollection(JsonNode node, Collector<String, ?, T> collector) {
        return Optional.ofNullable(node)
                .filter(Predicate.not(JsonNode::isNull))
                .filter(JsonNode::isArray)
                .map(e -> StreamSupport.stream(e.spliterator(), false)
                        .filter(Predicate.not(JsonNode::isNull))
                        .filter(JsonNode::isTextual)
                        .map(JsonNode::asText)
                        .filter(Predicate.not(String::isBlank))
                        .collect(collector)
                );
    }

    public static <T> Optional<T> create(final JsonNode root, Function<JsonNode, T> mapper) {
        return Optional.ofNullable(root)
                .filter(Predicate.not(JsonNode::isNull))
                .map(mapper::apply);
    }

    public static <T> T ensure(final JsonNode root, Function<JsonNode, T> mapper) {
        return create(root, mapper).orElseThrow();
    }

    public static <T> T ensureOptional(JsonNode root, Function<JsonNode, Optional<T>> mapper) {
        return ensure(root, mapper).orElseThrow();
    }

    public static Function<JsonNode, String> toTextMapper() {
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

    @UtilityClass
    public static class Text {
        public static Optional<String> create(JsonNode root) {
            return ContextUtilities.create(root, toTextMapper());
        }

        public static String ensure(JsonNode root) {
            return create(root).orElseThrow();
        }
    }
}
