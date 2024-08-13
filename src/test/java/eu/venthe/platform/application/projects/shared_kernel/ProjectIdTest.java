package eu.venthe.platform.application.projects.shared_kernel;

import eu.venthe.platform.organization.domain.OrganizationId;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.project.domain.source_configurations.SourceConfigurationId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProjectIdTest {
    @Test
    void deserialize() {
        assertThat(ProjectId.from("test/internal-name"))
                .isEqualTo(ProjectId.builder().configurationId(new SourceConfigurationId("default")).organizationId(new OrganizationId("default")).name("test/internal-name").build());
    }

    @Test
    void serialize() {
        assertThat(ProjectId.builder().configurationId(new SourceConfigurationId("test1")).organizationId(new OrganizationId("test2")).name("project/internal-name").build().serialize())
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
