package eu.venthe.pipeline.application.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.workflow.runs._archive.run_context.contexts.InputsContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MatrixContextTest extends AbstractContextTest {
    @Test
    void name() {
        // Given
        JsonNode jsonNode = readTree("""
                {
                  "os": "ubuntu-latest",
                  "node": 16
                }
                """);

        // When
        InputsContext stringJsonNodeMap = InputsContext.create(jsonNode).orElseThrow();

        // Then
        Assertions.assertThat(stringJsonNodeMap.getInputs())
                .containsEntry("node", nodeFactory().numberNode(16))
                .containsEntry("os", nodeFactory().textNode("ubuntu-latest"));
    }
}
