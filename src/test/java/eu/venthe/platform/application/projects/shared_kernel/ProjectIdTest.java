package eu.venthe.platform.application.repositorys.shared_kernel;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceRepositoryName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RepositoryNameTest {
    @Test
    void deserialize() {
        assertThat(RepositoryName.from("internal-name"))
                .isEqualTo(new RepositoryName(new OrganizationName("default"), new SourceRepositoryName("internal-name")));
    }

    @Test
    void serialize() {
        assertThat(new RepositoryName(new OrganizationName("test2"), new SourceRepositoryName("internal-name")).serialize())
                .isEqualTo("test2/internal-name");
    }

    @Test
    void deserialize_norepository() {
        assertThatThrownBy(() -> RepositoryName.from("/test"))
                .hasMessage("Repository organization is required")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deserialize_norepository2() {
        assertThatThrownBy(() -> RepositoryName.from("test/"))
                .hasMessage("Repository name is required")
                .isInstanceOf(IllegalArgumentException.class);
    }
}
