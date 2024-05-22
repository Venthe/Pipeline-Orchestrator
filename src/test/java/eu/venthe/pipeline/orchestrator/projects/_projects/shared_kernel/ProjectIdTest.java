package eu.venthe.pipeline.orchestrator.projects.shared_kernel;

import org.junit.jupiter.api.Test;

import static eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectId.from;
import static eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectId.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProjectIdTest {
    @Test
    void deserialize() {
        assertThat(from("test/project/internal-name"))
                .isEqualTo(of("test", "project/internal-name"));
    }

    @Test
    void serialize() {
        assertThat(of("test", "project/internal-name").serialize())
                .isEqualTo("test/project/internal-name");
    }

    @Test
    void deserialize_noproject() {
        assertThatThrownBy(() -> from("test"))
                .hasMessage("Project name is required")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deserialize_noproject2() {
        assertThatThrownBy(() -> from("test/"))
                .hasMessage("Project name is required")
                .isInstanceOf(IllegalArgumentException.class);
    }
}
