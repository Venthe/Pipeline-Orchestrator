package eu.venthe.pipeline.application.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.workflow.runs._archive.run_context.contexts.JobContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowDefinitionJobContextTest extends AbstractContextTest {
    @Test
    void name() {
        // Given
        JsonNode jsonNode = readTree("""
                {
                  "status": "success",
                  "container": {
                    "network": "github_network_53269bd575974817b43f4733536b200c"
                  },
                  "services": {
                    "postgres": {
                      "id": "60972d9aa486605e66b0dad4abb638dc3d9116f566579e418166eedb8abb9105",
                      "ports": {
                        "5432": "49153"
                      },
                      "network": "github_network_53269bd575974817b43f4733536b200c"
                    }
                  }
                }
                """);

        // When
        JobContext ensure = JobContext.ensure(jsonNode);

        // Then
        assertThat(ensure.getStatus()).isEqualTo("success");
        assertThat(ensure.getContainer()).satisfies(c -> {
            assertThat(c.getNetwork()).isEqualTo("github_network_53269bd575974817b43f4733536b200c");
        });
        assertThat(ensure.getServices()).containsEntry(
                "postgres",
                JobContext.Service.builder()
                        .id("60972d9aa486605e66b0dad4abb638dc3d9116f566579e418166eedb8abb9105")
                        .network("github_network_53269bd575974817b43f4733536b200c")
                        .port("5432", "49153")
                        .build()
        );
    }
}
