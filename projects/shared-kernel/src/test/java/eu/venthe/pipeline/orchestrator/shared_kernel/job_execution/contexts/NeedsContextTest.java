package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class NeedsContextTest extends AbstractContextTest {
    @Test
    void name() {
        // Given
        JsonNode root = readTree("""
                {
                  "build": {
                    "result": "success",
                    "outputs": {
                      "build_id": "123456"
                    }
                  },
                  "deploy": {
                    "result": "failure",
                    "outputs": {}
                  }
                }
                """);

        // When
        NeedsContext ensure = NeedsContext.ensure(root);

        // Then
        Assertions.assertThat(ensure.getJobs())
                .containsEntry(
                        "build",
                        NeedsContext.JobNeed.builder()
                                .output("build_id", "123456")
                                .result("success")
                                .build()
                )
                .containsEntry(
                        "deploy",
                        NeedsContext.JobNeed.builder()
                                .result("failure")
                                .build()
                );
    }

}
