package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive._1.context.job_execution.contexts.InputsContext;
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
