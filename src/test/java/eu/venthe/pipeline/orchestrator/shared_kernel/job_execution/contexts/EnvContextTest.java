package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.workflows.runs._archive.run_context.contexts.EnvContext;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnvContextTest extends AbstractContextTest {
    @Test
    void name() {
        // Given
        JsonNode jsonNode = readTree("""
                {
                  "first_name": "Mona",
                  "super_duper_var": "totally_awesome"
                }
                """);

        // When
        EnvContext envContext = EnvContext.ensure(jsonNode);

        // Then
        assertThat(envContext.getEnvironmentVariables())
                .hasEntrySatisfying("first_name", v -> assertThat(v).isEqualTo("Mona"))
                .hasEntrySatisfying("super_duper_var", v -> assertThat(v).isEqualTo("totally_awesome"));
    }

    @Test
    void name2() {
        // Given
        JsonNode jsonNode = readTree("""
                {
                  "first_name": "Mona",
                  "super_duper_var": "totally_awesome"
                }
                """);

        // When
        JsonNode envContext = objectMapper.valueToTree(EnvContext.ensure(jsonNode));

        // Then
        assertThat(envContext).isEqualTo(jsonNode);
    }

    @SneakyThrows
    @Test
    void name3() {
        // Given
        String data = """
                {
                  "first_name": "Mona",
                  "super_duper_var": "totally_awesome"
                }
                """;
        JsonNode jsonNode = readTree(data);
        EnvContext build = objectMapper.readValue(data, EnvContext.class);

        // When
        JsonNode envContext = objectMapper.valueToTree(build);

        // Then
        assertThat(envContext).isEqualTo(jsonNode);
    }
}
