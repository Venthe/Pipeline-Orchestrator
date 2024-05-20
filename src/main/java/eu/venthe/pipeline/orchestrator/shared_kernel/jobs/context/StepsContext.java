package eu.venthe.pipeline.orchestrator.shared_kernel.jobs.context;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

// TODO: Implement context
@RequiredArgsConstructor
public class StepsContext {
    private final ArrayNode root;

    public static Optional<StepsContext> create(JsonNode root) {

        JsonNode steps = root.get("steps");

        if (steps == null || steps.isNull()) {
            return Optional.empty();
        }

        if (!steps.isArray()) throw new IllegalArgumentException();

        return Optional.of(new StepsContext((ArrayNode) steps));
    }

    public ArrayNode getRaw() {
        return root;
    }
}
