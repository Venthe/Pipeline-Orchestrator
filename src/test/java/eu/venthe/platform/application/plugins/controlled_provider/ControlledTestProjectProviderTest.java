/*
package eu.venthe.pipeline.orchestrator.plugins.controlled_provider;

import eu.venthe.pipeline.orchestrator.repositorys.adapter.template.model.RepositoryDto;
import eu.venthe.pipeline.orchestrator.repositorys.adapter.plugin.controlled_provider.ControlledTestRepositoryProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

class ControlledTestRepositoryProviderTest {
    private ControlledTestRepositoryProvider provider;

    @BeforeEach
    void setup() {
        provider = new ControlledTestRepositoryProvider(new RestTemplate());
    }

    @Test
    void name() {
        Collection<RepositoryDto> repositorys = provider.getRepository();
        assertThat(repositorys)
                .containsExactlyInAnyOrder(new RepositoryDto(RepositoryDto.Status.ACTIVE, "Example-A"));
    }
}
*/
