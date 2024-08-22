/*
package eu.venthe.pipeline.orchestrator.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

@Slf4j
@Service
public class MockRepositoryLoader {
    private static final String ROOT = "./repositorys";
    private final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    @Test
    void test() throws IOException {

        JsonNode repositorysDefinition = loadYaml("definition.yaml");
        JsonNode repositorys = repositorysDefinition.get("repositorys");
        for (Map.Entry<String, JsonNode> repository : repositorys.properties()) {
            String repositoryKey = repository.getKey();
            JsonNode refs = repository.getValue().get("refs");
            for (Map.Entry<String, JsonNode> ref : refs.properties()) {
                String refKey = ref.getKey();
                JsonNode workflows = ref.getValue().get("workflows");
                for (JsonNode el : workflows) {
                    String path = el.get("path").asText();
                    String name = el.get("name").asText();

                    log.info("[{}:{}]Loading {} from {}", repositoryKey, refKey, name, path);
                    log.info("{}", loadYaml(path));
                }
            }
        }
    }

    private JsonNode loadYaml(String path) throws IOException {
        String pathWithRoot = Path.of(ROOT, path).toString();
        URL url = ClassLoader.getSystemResource(pathWithRoot);
        return objectMapper.readTree(url);
    }
}
*/
