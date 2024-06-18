package eu.venthe.pipeline.orchestrator.projects.shared_kernel;

import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProjectIdTest {
    @Test
    void deserialize() {
        assertThat(ProjectId.from("test/internal-name"))
                .isEqualTo(ProjectId.of(new SourceConfigurationId("default"), new OrganizationId("default"), "test/internal-name"));
    }

    @Test
    void serialize() {
        assertThat(ProjectId.of(new SourceConfigurationId("test1"), new OrganizationId("test2"), "project/internal-name").serialize())
                .isEqualTo("test1:test2/project/internal-name");
    }

    @Test
    void deserialize_noproject() {
        assertThatThrownBy(() -> ProjectId.from("test"))
                .hasMessage("Project name is required")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deserialize_noproject2() {
        assertThatThrownBy(() -> ProjectId.from("test/"))
                .hasMessage("Project name is required")
                .isInstanceOf(IllegalArgumentException.class);
    }
}
