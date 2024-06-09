package eu.venthe.pipeline.orchestrator.projects.shared_kernel;

import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectId;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.SourceConfigurationId;
import org.junit.jupiter.api.Test;

import static eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectId.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProjectIdTest {
    @Test
    void deserialize() {
        assertThat(from("test/project/internal-name"))
                .isEqualTo(ProjectId.of(new SourceConfigurationId("test"), "project/internal-name"));
    }

    @Test
    void serialize() {
        assertThat(ProjectId.of(new SourceConfigurationId("test"), "project/internal-name").serialize())
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
