package eu.venthe.pipeline.application.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.workflows.runs._archive.run_context.contexts.SecretsContext;
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
