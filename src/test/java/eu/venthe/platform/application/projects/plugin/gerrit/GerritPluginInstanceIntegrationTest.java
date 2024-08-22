package eu.venthe.platform.application.repositorys.plugin.gerrit;

import eu.venthe.platform.application.AbstractIntegrationTest;
import eu.venthe.platform.source_configuration.domain.plugins.gerrit.GerritConfiguration;
import eu.venthe.platform.source_configuration.domain.plugins.gerrit.GerritPluginInstance;
import eu.venthe.platform.source_configuration.domain.plugins.template.RepositorySourcePluginInstance;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;

@Slf4j
class GerritPluginInstanceIntegrationTest extends AbstractIntegrationTest {
    static GerritConfiguration CONFIGURATION = GerritConfiguration.builder()
            .basePath("http://localhost:15480")
            .username("admin")
            .password("secret")
            .build();

    RepositorySourcePluginInstance plugin = new GerritPluginInstance(CONFIGURATION);


    @Test
    void listRepository() {
        var retrieve = SimpleStringClient.mutate(restClient)
                .method(HttpMethod.POST)
                .uri("/gerrit/handle/patchset-created")
                .body("Foobar")
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity();

        Assertions.assertThat(retrieve).satisfies(e -> Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200)));
    }
}
