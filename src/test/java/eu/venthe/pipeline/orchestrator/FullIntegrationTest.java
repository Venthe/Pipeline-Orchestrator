package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.projects.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.infrastructure.ProjectRepository;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.application.ProjectSourcesManager;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.PropertyName;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.TextSuppliedConfigurationProperty;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FullIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    ProjectSourcesManager projectSourcesManager;
    @Autowired
    ProjectRepository projectRepository;

    @Test
    void name() {
        ProjectsSourceConfiguration register = projectSourcesManager.register("Test", "Gerrit",
                SuppliedProperties.builder()
                        .property(new PropertyName("basePath"), new TextSuppliedConfigurationProperty("http://localhost:15480"))
                        .property(new PropertyName("username"), new TextSuppliedConfigurationProperty("admin"))
                        .property(new PropertyName("password"), new TextSuppliedConfigurationProperty("secret"))
                        .build());
        register.synchronize();
        // At this point, auto synchronization should happen. Let's wait for it.
        Awaitility.await("Synchronization done").until(() -> !projectRepository.findAll().isEmpty());

        Awaitility.await("Project found")
                .untilAsserted(() -> Assertions.assertThat(projectRepository.exists(ProjectId.of(new ProjectsSourceConfigurationId("Test"), "All-Users"))).isTrue());
    }
}
