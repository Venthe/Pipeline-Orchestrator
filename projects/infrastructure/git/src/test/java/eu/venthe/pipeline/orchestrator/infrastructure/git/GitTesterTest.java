package eu.venthe.pipeline.orchestrator.infrastructure.git;

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
