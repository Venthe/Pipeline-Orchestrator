package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.context.job_execution.contexts.RunnerContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RunnerContextTest extends AbstractContextTest {
    @Test
    void name() {
        // Given
        JsonNode root = readTree("""
                {
                  "os": "Linux",
                  "arch": "X64",
                  "name": "GitHub Actions 2",
                  "tool_cache": "/opt/hostedtoolcache",
                  "temp": "/home/runner/work/_temp"
                }
                """);

        // When
        RunnerContext runnerContext = RunnerContext.ensure(root);

        // Then
        assertThat(runnerContext.getOs()).isEqualTo("Linux");
        assertThat(runnerContext.getArch()).isEqualTo("X64");
        assertThat(runnerContext.getName()).isEqualTo("GitHub Actions 2");
        assertThat(runnerContext.getToolCache()).isEqualTo("/opt/hostedtoolcache");
        assertThat(runnerContext.getTemp()).isEqualTo("/home/runner/work/_temp");
    }
}
