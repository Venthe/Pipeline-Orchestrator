package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.run_context.contexts.VarsContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VarsContextTest extends AbstractContextTest {
    @Test
    void name() {
        // Given
        JsonNode jsonNode = readTree("""
                {
                  "mascot": "Mona"
                }
                """);

        // When
        VarsContext ensure = VarsContext.ensure(jsonNode);

        // Then
        assertThat(ensure.getCustomConfigurationVariables())
                .hasEntrySatisfying("mascot", v -> assertThat(v).isEqualTo("Mona"));
    }
}
