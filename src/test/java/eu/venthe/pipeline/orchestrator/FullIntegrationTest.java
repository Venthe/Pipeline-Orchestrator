package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.projects.projects.domain.ProjectRepository;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.application.ProjectSourcesManager;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.SuppliedProperties;
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
        projectSourcesManager.register("Test", "Gerrit", SuppliedProperties.builder().build());
        // At this point, auto synchronization should happen. Let's wait for it.
        Awaitility.await("Synchronization done").until(() -> !projectRepository.findAll().isEmpty());
    }
}
