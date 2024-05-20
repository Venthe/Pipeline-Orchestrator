package eu.venthe.pipeline.orchestrator.projects.domain.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class ObjectNodeUtilities {
    public static String pathHelper(String... elements) {
        return Arrays.stream(elements).filter(Objects::nonNull).collect(Collectors.joining("."));
    }

    public static void forceDeepSet(ObjectMapper mapper, JsonNode node, String path, JsonNode value) {
        deepSet(mapper, node, path, value, false);
    }

    public static void deepSetIfNotPresent(ObjectMapper mapper, JsonNode node, String path, JsonNode value) {
        deepSet(mapper, node, path, value, true);
    }

    public static void deepSetIfNotPresent(ObjectMapper mapper, JsonNode node, Map<String, JsonNode> changes) {
        deepSet(mapper, node, changes, true);
    }

    public static void deepSet(ObjectMapper mapper, JsonNode node, Map<String, JsonNode> changes, boolean ifNotPresent) {
        changes.forEach((key, value) -> deepSet(mapper, node, key, value, ifNotPresent));
    }

    public static void deepSet(ObjectMapper mapper, JsonNode node, String path, JsonNode value, boolean ifNotPresent) {
        String[] keys = path.split("\\.");
        JsonNode currentNode = node;
        for (int i = 0; i < keys.length - 1; i++) {
            String key = keys[i];
            if (currentNode.isArray()) {
                int index = Integer.parseInt(key);
                if (index >= currentNode.size()) {
                    ArrayNode arrayNode = mapper.createArrayNode();
                    ((ArrayNode) currentNode).add(arrayNode);
                }
                currentNode = currentNode.get(index);
            } else {
                JsonNode child = currentNode.get(key);
                if (child == null || !child.isObject()) {
                    child = mapper.createObjectNode();
                    ((ObjectNode) currentNode).set(key, child);
                }
                currentNode = child;
            }
        }
        if (currentNode.isArray()) {
            int index = Integer.parseInt(keys[keys.length - 1]);
            while (currentNode.size() <= index) {
                ((ArrayNode) currentNode).addNull();
            }
            if (currentNode.get(index) != null && !currentNode.get(index).isMissingNode() && ifNotPresent) {
                return;
            }
            ((ArrayNode) currentNode).set(index, value);
        } else {
            if (currentNode.get(keys[keys.length - 1]) != null && !currentNode.get(keys[keys.length - 1]).isMissingNode() && ifNotPresent) {
                return;
            }
            ((ObjectNode) currentNode).set(keys[keys.length - 1], value);
        }
    }
}
