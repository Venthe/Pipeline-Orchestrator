package eu.venthe.platform.workflow.definition._archive;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

// TODO: Implement context
@RequiredArgsConstructor
public class StepsContext2 {
    private final ArrayNode root;

    public static Optional<StepsContext2> create(JsonNode root) {

        JsonNode steps = root.get("steps");

        if (steps == null || steps.isNull()) {
            return Optional.empty();
        }

        if (!steps.isArray()) throw new IllegalArgumentException();

        return Optional.of(new StepsContext2((ArrayNode) steps));
    }

    public ArrayNode getRaw() {
        return root;
    }
}
