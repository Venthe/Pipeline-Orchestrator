/*
package eu.venthe.pipeline.orchestrator.plugins.gerrit._archive.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GerritResponseMapper {
    private final ObjectMapper objectMapper;

    public JsonNode mapToJsonNode(String body) {
        return Optional.ofNullable(body)
                .map(GerritResponseMapper::removeGerritMagicPrefix)
                .map(this::readTree)
                .orElseThrow();
    }

    private JsonNode readTree(String content) {
        try {
            return objectMapper.readTree(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String removeGerritMagicPrefix(String body) {
        return body.replaceAll("\\)]}'\\n", "");
    }
}
*/
