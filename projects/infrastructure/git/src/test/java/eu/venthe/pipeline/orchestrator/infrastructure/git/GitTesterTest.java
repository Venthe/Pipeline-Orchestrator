package eu.venthe.pipeline.orchestrator.infrastructure.git;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class GitTesterTest {
    @SneakyThrows
    @Test
    void name() {
        GitTester.cloneRemoteRepository("https://gerrit.home.arpa/a/actions");
    }
}
