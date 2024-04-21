package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
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
        EnvContext ensure = EnvContext.ensure(jsonNode);

        // Then
        assertThat(ensure.getEnvironmentVariables())
                .hasEntrySatisfying("first_name", v -> assertThat(v).isEqualTo("Mona"))
                .hasEntrySatisfying("super_duper_var", v -> assertThat(v).isEqualTo("totally_awesome"));
    }
}
