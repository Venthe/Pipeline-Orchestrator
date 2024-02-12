package eu.venthe.pipeline.orchestrator.plugins.plugin.git;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GitTesterTest {
    @SneakyThrows
    @Test
    void name() {
        GitTester.cloneRemoteRepository("https://gerrit.home.arpa/a/actions");
    }
}