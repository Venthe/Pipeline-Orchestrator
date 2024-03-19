package eu.venthe.pipeline.orchestrator.projects.domain.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.StreamSupport;

@UtilityClass
public class ContextUtilities {
    public static <T> Optional<T> get(Function<ObjectNode, T> creator, JsonNode root) {
        return Optional.ofNullable(root)
                .filter(Predicate.not(JsonNode::isNull))
                .map(node -> creator.apply((ObjectNode) node));
    }

    public static Optional<String> toTextual(JsonNode root) {
        return Optional.ofNullable(root)
                .filter(Predicate.not(JsonNode::isNull))
                .filter(JsonNode::isTextual)
                .map(JsonNode::asText);
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

}
