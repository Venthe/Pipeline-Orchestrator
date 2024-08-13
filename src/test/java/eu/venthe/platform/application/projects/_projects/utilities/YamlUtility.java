package eu.venthe.platform.application.projects.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class YamlUtility {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

    @SneakyThrows
    public static JsonNode parseYaml(String value) {
        return OBJECT_MAPPER.readTree(value);
    }

    public static JsonNodeFactory getNodeFactory() {
        return OBJECT_MAPPER.getNodeFactory();
    }

    public static ObjectNode createObjectNode() {
        return OBJECT_MAPPER.createObjectNode();
    }
}
