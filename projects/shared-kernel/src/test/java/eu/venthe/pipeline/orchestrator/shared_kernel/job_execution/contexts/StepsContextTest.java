package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class StepsContextTest extends AbstractContextTest {
    @Test
    void name() {
        // Given
        JsonNode jsonNode = readTree("""
                {
                  "checkout": {
                    "outputs": {},
                    "outcome": "success",
                    "conclusion": "success"
                  },
                  "generate_number": {
                    "outputs": {
                      "random_number": "1"
                    },
                    "outcome": "success",
                    "conclusion": "success"
                  }
                }
                """);

        // When
        StepsContext ensure = StepsContext.ensure(jsonNode);

        // Then
        Assertions.assertThat(ensure.getSteps())
                .containsEntry("checkout", StepsContext.StepContext.builder()
                        .outcome("success")
                        .conclusion("success")
                        .build())
                .containsEntry("generate_number", StepsContext.StepContext.builder()
                        .outcome("success")
                        .conclusion("success")
                        .output("random_number", "1")
                        .build());
    }

}
