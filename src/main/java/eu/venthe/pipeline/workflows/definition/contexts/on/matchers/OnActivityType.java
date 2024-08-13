package eu.venthe.pipeline.workflows.definition.contexts.on.matchers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.application.utilities.CollectionUtilities;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
public class OnActivityType {
    private final JsonNode root;
    private final Optional<String> text;
    private final Optional<List<String>> array;

    private OnActivityType(JsonNode root) {
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

    public static Optional<OnActivityType> create(JsonNode root) {
        return Optional.ofNullable(root.get("types"))
                .filter(Predicate.not(JsonNode::isNull))
                .map(OnActivityType::new);
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
