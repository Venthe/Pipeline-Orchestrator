/*
package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.repositorys.api.dto.RepositoryDto;
import eu.venthe.pipeline.orchestrator.repositorys.api.RepositoryQueryService;
import eu.venthe.pipeline.orchestrator.repositorys._archive.api.RepositorySourceConfigurationCommandService;
import eu.venthe.pipeline.orchestrator.repositorys._archive.api.RepositorySourceConfigurationQueryService;
import eu.venthe.pipeline.orchestrator.repositorys._archive.api.ReadRepositorySourceConfigurationDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class RepositoryIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    RepositorySourceConfigurationCommandService repositorysSourceConfigurationService;
    @Autowired
    RepositorySourceConfigurationQueryService repositorysSourceConfigurationQueryService;

    @Autowired
    RepositoryQueryService repositorysService;

    @Test
    @Disabled
    void name() {
        Map<String, String> properties = Map.of(
                "username", "admin",
                "password", "secret",
                "basePath", "http://localhost:15480"
        );
        String id = "gerrit_1";
        String sourceType = "gerrit";
        String repositoryId = repositorysSourceConfigurationService.addRepositorySourceConfiguration(id, sourceType, properties);

        Set<ReadRepositorySourceConfigurationDto> repositoryConfigurations = repositorysSourceConfigurationQueryService.listConfigurations();
        await().untilAsserted(() -> {
            assertThat(repositoryConfigurations).singleElement()
                    .isEqualTo(new ReadRepositorySourceConfigurationDto(id, sourceType));
        });

        repositorysSourceConfigurationService.synchronizeRepository(repositoryId);

        await().untilAsserted(() -> {
            Collection<RepositoryDto> repositorys = repositorysService.listRepository();
            assertThat(repositorys).hasSize(2).containsExactlyInAnyOrder(
                    new RepositoryDto("All-Repository", id),
                    new RepositoryDto("All-Users", id)
            );
        });
    }
}
*/
