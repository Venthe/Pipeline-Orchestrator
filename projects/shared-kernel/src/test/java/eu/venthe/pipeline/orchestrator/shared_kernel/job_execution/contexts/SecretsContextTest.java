package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SecretsContextTest extends AbstractContextTest {
    @Test
    void name() {
        // Given
        JsonNode root = readTree("""
                {
                  "GITHUB_TOKEN": "***",
                  "NPM_TOKEN": "***",
                  "SUPERSECRET": "***"
                }
                """);

        // When
        SecretsContext context = SecretsContext.ensure(root);

        // Then
        Assertions.assertThat(context.getSecrets())
                .containsEntry("GITHUB_TOKEN", "***")
                .containsEntry("NPM_TOKEN", "***")
                .containsEntry("SUPERSECRET", "***");
    }
}
