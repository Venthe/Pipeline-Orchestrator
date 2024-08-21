package eu.venthe.platform.application.projects.shared_kernel;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceProjectId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProjectIdTest {
    @Test
    void deserialize() {
        assertThat(ProjectId.from("internal-name"))
                .isEqualTo(new ProjectId(new OrganizationName("default"), new SourceProjectId("internal-name")));
    }

    @Test
    void serialize() {
        assertThat(new ProjectId(new OrganizationName("test2"), new SourceProjectId("internal-name")).serialize())
                .isEqualTo("test2/internal-name");
    }

    @Test
    void deserialize_noproject() {
        assertThatThrownBy(() -> ProjectId.from("/test"))
                .hasMessage("Project organization is required")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deserialize_noproject2() {
        assertThatThrownBy(() -> ProjectId.from("test/"))
                .hasMessage("Project name is required")
                .isInstanceOf(IllegalArgumentException.class);
    }
}
