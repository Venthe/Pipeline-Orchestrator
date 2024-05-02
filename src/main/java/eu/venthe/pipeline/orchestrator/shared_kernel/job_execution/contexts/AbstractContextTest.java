package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;

abstract public class AbstractContextTest {
    protected static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
    }

    @SneakyThrows
    protected JsonNode readTree(String data) {
        return objectMapper.readTree(data);
    }

    protected JsonNodeFactory nodeFactory() {
        return objectMapper.getNodeFactory();
    }
}
