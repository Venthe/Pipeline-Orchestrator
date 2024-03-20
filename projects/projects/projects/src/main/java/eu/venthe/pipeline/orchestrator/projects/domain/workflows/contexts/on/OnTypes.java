package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
public class OnTypes {
    private final JsonNode root;
    private final Optional<String> text;
    private final Optional<List<String>> array;

    public OnTypes(JsonNode root) {
        this.root = root;

        text = Optional.ofNullable(this.root)
                .filter(JsonNode::isTextual)
                .map(JsonNode::asText);
        array = Optional.ofNullable(this.root)
                .map(JsonNode::elements)
                .map(elements -> CollectionUtilities.iteratorToStream(elements)
                        .map(JsonNode::asText)
                        .toList()
                );

    }

    public static Optional<OnTypes> create(ObjectNode root) {
        return Optional.ofNullable(root.get("types"))
                .filter(Predicate.not(JsonNode::isNull))
                .map(OnTypes::new);
    }

    public Optional<String> text() {
        return text;
    }

    public Optional<List<String>> array() {
        return array;
    }

    public boolean match(String action) {
        List<Boolean> votes = new ArrayList<>();

        text().ifPresent(text -> votes.add(text.equalsIgnoreCase(action)));
        array().ifPresent(arr -> arr.stream().map(v -> v.equalsIgnoreCase(action)).forEach(votes::add));

        boolean result = votes.isEmpty() || votes.stream().allMatch(e -> e.equals(true));
        log.info("Matching action - {}", result);
        return result;
    }
}
