package eu.venthe.pipeline.orchestrator._archive2.common_contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class JsonInputs {
    private final ObjectNode root;

    @Getter
    private final Map<String, JsonNode> values = new HashMap<>();

    public JsonInputs(JsonNode root) {
        if (root == null) throw new IllegalArgumentException();
        if (!root.isObject()) throw new IllegalArgumentException();

        this.root = (ObjectNode) root;

        for (var entry : this.root.properties()) {
            JsonNode value = entry.getValue();
            if (!(value.isTextual() || value.isBoolean() || value.isNumber())) throw new IllegalArgumentException();

            this.values.put(entry.getKey(), value);
        }

    }
}
