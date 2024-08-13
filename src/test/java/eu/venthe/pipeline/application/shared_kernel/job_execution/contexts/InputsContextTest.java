package eu.venthe.pipeline.application.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.workflow.runs._archive.run_context.contexts.InputsContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class InputsContextTest extends AbstractContextTest {
    @Test
    void name() {
        // Given
        JsonNode jsonNode = readTree("""
                {
                  "build_id": 123456768,
                  "deploy_target": "deployment_sys_1a",
                  "perform_deploy": true
                }
                """);

        // When
        InputsContext stringJsonNodeMap = InputsContext.create(jsonNode).orElseThrow();

        // Then
        Assertions.assertThat(stringJsonNodeMap.getInputs())
                .containsEntry("build_id", nodeFactory().numberNode(123456768))
                .containsEntry("deploy_target", nodeFactory().textNode("deployment_sys_1a"))
                .containsEntry("perform_deploy", nodeFactory().booleanNode(true));
    }
}
