package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class JobsContextTest extends AbstractContextTest {
    // Given
    @Test
    void name() {
        JsonNode jsonNode = readTree("""
                {
                  "example_job": {
                    "result": "success",
                    "outputs": {
                      "output1": "hello",
                      "output2": "world"
                    }
                  }
                }
                """);

        // When
        Map<String, JobsContext> ensure = JobsContext.ensure(jsonNode);

        // Then
        Assertions.assertThat(ensure).containsEntry("example_job", JobsContext.builder()
                .result("success")
                .output("output1", "hello")
                .output("output2", "world")
                .build());
    }
}
