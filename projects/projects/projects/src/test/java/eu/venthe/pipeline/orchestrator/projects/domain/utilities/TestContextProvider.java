package eu.venthe.pipeline.orchestrator.projects.domain.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static eu.venthe.pipeline.orchestrator.projects.domain.utilities.ObjectNodeUtilities.*;

public class TestContextProvider {
    private final ObjectMapper objectMapper;

    public TestContextProvider(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void sampleUser(JsonNode eventTree) {
        sampleUser(eventTree, null);
    }

    public void sampleUser(JsonNode eventTree, String prefix) {
        deepSetIfNotPresent(objectMapper, eventTree, pathHelper(prefix, "id"), objectMapper.getNodeFactory().textNode(UUID.randomUUID().toString()));
        deepSetIfNotPresent(objectMapper, eventTree, pathHelper(prefix, "login"), objectMapper.getNodeFactory().textNode("Login"));
        deepSetIfNotPresent(objectMapper, eventTree, pathHelper(prefix, "userType"), objectMapper.getNodeFactory().textNode("user"));
    }

    public void repository(JsonNode eventTree) {
        repository(eventTree, null);
    }

    public void repository(JsonNode eventTree, String prefix) {
        deepSetIfNotPresent(objectMapper, eventTree, pathHelper(prefix, "id"), objectMapper.getNodeFactory().textNode(UUID.randomUUID().toString()));
        deepSetIfNotPresent(objectMapper, eventTree, pathHelper(prefix, "name"), objectMapper.getNodeFactory().textNode(UUID.randomUUID().toString()));
        deepSetIfNotPresent(objectMapper, eventTree, pathHelper(prefix, "fullName"), objectMapper.getNodeFactory().textNode(UUID.randomUUID().toString()));
        deepSetIfNotPresent(objectMapper, eventTree, pathHelper(prefix, "description"), objectMapper.getNodeFactory().textNode(UUID.randomUUID().toString()));
        deepSetIfNotPresent(objectMapper, eventTree, pathHelper(prefix, "url"), objectMapper.getNodeFactory().textNode("http://dummy"));
        deepSetIfNotPresent(objectMapper, eventTree, pathHelper(prefix, "visibility"), objectMapper.getNodeFactory().textNode("public"));
        sampleUser(eventTree, pathHelper(prefix, "owner"));
    }
}
