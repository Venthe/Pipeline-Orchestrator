package eu.venthe.platform.application.projects.plugin.gerrit;

import eu.venthe.platform.application.AbstractIntegrationTest;
import eu.venthe.platform.application.config.RegisterPluginEndpoints;
import eu.venthe.platform.source_configuration.plugins.gerrit.GerritConfiguration;
import eu.venthe.platform.source_configuration.plugins.gerrit.GerritPluginInstance;
import eu.venthe.platform.source_configuration.plugins.template.model.SourceType;
import eu.venthe.platform.source_configuration.plugins.template.ProjectSourcePlugin;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    ProjectSourcePlugin.PluginInstance plugin = new GerritPluginInstance(CONFIGURATION, new SourceType("gerrit"));

    @Autowired
    RegisterPluginEndpoints registerPluginEndpoints;

    @Test
    void listProjects() {
        registerPluginEndpoints.register(plugin);

        var retrieve = SimpleStringClient.mutate(restClient)
                .method(HttpMethod.POST)
                .uri("/gerrit/handle/patchset-created")
                .body("Foobar")
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity();

        Assertions.assertThat(retrieve).satisfies(e -> {
            Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        });
    }
}
