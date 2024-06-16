package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.context.job_execution.contexts.JobsContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
        JobsContext ensure = JobsContext.ensure(jsonNode);

        // Then
        Assertions.assertThat(ensure.getInputs()).containsEntry("example_job", JobsContext.JobJobsContext.builder()
                .result("success")
                .output("output1", "hello")
                .output("output2", "world")
                .build());
    }
}
