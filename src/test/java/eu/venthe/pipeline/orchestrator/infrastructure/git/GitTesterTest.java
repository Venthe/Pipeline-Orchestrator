package eu.venthe.pipeline.orchestrator.infrastructure.git;

import eu.venthe.pipeline.orchestrator.projects_source.adapter.plugin.controlled_test_source.GitTester;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class GitTesterTest {
    @Disabled
    @SneakyThrows
    @Test
    void name() {
        GitTester.cloneRemoteRepository("https://gerrit.home.arpa/a/actions");
    }
}
