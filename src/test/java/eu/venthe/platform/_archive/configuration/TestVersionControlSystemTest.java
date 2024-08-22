/*
package eu.venthe.pipeline.orchestrator.configuration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TestVersionControlSystemTest {

    @Test
    void smokeTest() {
        TestVersionControlSystem vcs = new TestVersionControlSystem();

        vcs.addFile(new TestVersionControlSystem.FileRef("Example-Repository", "main", ".workflows/a.yaml"), "1");
        vcs.addFile(new TestVersionControlSystem.FileRef("Example-Repository", "main", ".workflows/b.yaml"), "2");
        vcs.addFile(new TestVersionControlSystem.FileRef("Example-Repository", "main", ".workflows/c.json"), "3");

        vcs.addFile(new TestVersionControlSystem.FileRef("Example-Repository", "main2", ".workflows/a.yaml"), "x1");
        vcs.addFile(new TestVersionControlSystem.FileRef("Example-Repository2", "main", ".workflows/a.yaml"), "x2");
        vcs.addFile(new TestVersionControlSystem.FileRef("Example-Repository", "main", ".workflows/dir/c.json"), "x3");
        vcs.addFile(new TestVersionControlSystem.FileRef("Example-Repository", "main", "a.yaml"), "x4");
        vcs.addFile(new TestVersionControlSystem.FileRef("Example-Repository", "main", ".workflows/dir/a.yaml"), "x5");

        List<String> files = vcs.getFiles("Example-Repository", "main", ".workflows/**//*.yaml");
        Assertions.assertThat(files).hasSize(3);
    }
}
*/
