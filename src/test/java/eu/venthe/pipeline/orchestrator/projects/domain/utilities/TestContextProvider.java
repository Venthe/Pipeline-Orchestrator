/*
package eu.venthe.pipeline.orchestrator.projects.domain.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static eu.venthe.pipeline.orchestrator.projects.domain.utilities.ObjectNodeUtilities.pathHelper;

public class TestContextProvider {
    private final ObjectMapper objectMapper;

    public TestContextProvider(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<String, JsonNode> sampleUser(String prefix) {
        return Map.of(
                pathHelper(prefix, "id"), objectMapper.getNodeFactory().textNode(UUID.randomUUID().toString()),
                pathHelper(prefix, "login"), objectMapper.getNodeFactory().textNode("Login"),
                pathHelper(prefix, "userType"), objectMapper.getNodeFactory().textNode("user")
        );
    }

    public Map<String, JsonNode> repository(String prefix) {
        Map<String, JsonNode> owner = sampleUser(pathHelper(prefix, "owner"));
        Map<String, JsonNode> repository = Map.ofEntries(
                Map.entry(pathHelper(prefix, "id"), objectMapper.getNodeFactory().textNode(UUID.randomUUID().toString())),
                Map.entry(pathHelper(prefix, "name"), objectMapper.getNodeFactory().textNode(UUID.randomUUID().toString())),
                Map.entry(pathHelper(prefix, "fullName"), objectMapper.getNodeFactory().textNode(UUID.randomUUID().toString())),
                Map.entry(pathHelper(prefix, "description"), objectMapper.getNodeFactory().textNode(UUID.randomUUID().toString())),
                Map.entry(pathHelper(prefix, "url"), objectMapper.getNodeFactory().textNode("http://dummy")),
                Map.entry(pathHelper(prefix, "visibility"), objectMapper.getNodeFactory().textNode("public"))
        );
        return Stream.of(
                        owner.entrySet(),
                        repository.entrySet()
                )
                .flatMap(Collection::stream)
                .collect(toMap());
    }
}
*/
