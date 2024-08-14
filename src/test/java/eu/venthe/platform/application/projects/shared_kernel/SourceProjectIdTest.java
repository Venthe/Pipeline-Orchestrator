package eu.venthe.platform.application.projects.shared_kernel;

import eu.venthe.platform.namespace.domain.NamespaceName;
import eu.venthe.platform.shared_kernel.project.ProjectId;
import eu.venthe.platform.source_configuration.SourceConfigurationId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SourceProjectIdTest {
    @Test
    void deserialize() {
        assertThat(ProjectId.from("test/internal-name"))
                .isEqualTo(ProjectId.builder().configurationId(new SourceConfigurationId("default")).namespaceName(new NamespaceName("default")).name("test/internal-name").build());
    }

    @Test
    void serialize() {
        assertThat(ProjectId.builder().configurationId(new SourceConfigurationId("test1")).namespaceName(new NamespaceName("test2")).name("project/internal-name").build().serialize())
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
