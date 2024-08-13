package eu.venthe.pipeline.application.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.workflow.runs._archive.run_context.contexts.StrategyContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StrategyContextTest extends AbstractContextTest {
    @Test
    void name() {
        // Given
        JsonNode root = readTree("""
                {
                  "fail_fast": true,
                  "job_index": 3,
                  "job_total": 4,
                  "max_parallel": 4
                }
                """);

        // When
        StrategyContext context = StrategyContext.ensure(root);

        // Then
        Assertions.assertThat(context.getFailFast()).isTrue();
        Assertions.assertThat(context.getJobIndex()).isEqualTo(3);
        Assertions.assertThat(context.getJobTotal()).isEqualTo(4);
        Assertions.assertThat(context.getMaxParallel()).isEqualTo(4);
    }

}
