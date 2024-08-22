package eu.venthe.platform.application.repositorys.shared_kernel;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.repository.domain.RepositoryId;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceRepositoryId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RepositoryIdTest {
    @Test
    void deserialize() {
        assertThat(RepositoryId.from("internal-name"))
                .isEqualTo(new RepositoryId(new OrganizationName("default"), new SourceRepositoryId("internal-name")));
    }

    @Test
    void serialize() {
        assertThat(new RepositoryId(new OrganizationName("test2"), new SourceRepositoryId("internal-name")).serialize())
                .isEqualTo("test2/internal-name");
    }

    @Test
    void deserialize_norepository() {
        assertThatThrownBy(() -> RepositoryId.from("/test"))
                .hasMessage("Repository organization is required")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deserialize_norepository2() {
        assertThatThrownBy(() -> RepositoryId.from("test/"))
                .hasMessage("Repository name is required")
                .isInstanceOf(IllegalArgumentException.class);
    }
}
