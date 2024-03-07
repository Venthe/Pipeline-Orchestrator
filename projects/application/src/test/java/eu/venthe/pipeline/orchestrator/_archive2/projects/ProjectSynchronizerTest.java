package eu.venthe.pipeline.orchestrator._archive2.projects;

import eu.venthe.pipeline.orchestrator.AbstractIntegrationTest;
import eu.venthe.pipeline.orchestrator._archive2.application.WorkflowTriggerService;
import eu.venthe.pipeline.orchestrator._archive2.event_handlers.EventHandlerProvider;
import eu.venthe.pipeline.orchestrator._archive2.event_handlers.handlers.WorkflowDispatchEventHandler;
import eu.venthe.pipeline.orchestrator._archive2.projects.ProjectSynchronizer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static eu.venthe.pipeline.orchestrator.Application.disableSSL;

@MockBean(classes = {WorkflowTriggerService.class, EventHandlerProvider.class, WorkflowDispatchEventHandler.class})
class ProjectSynchronizerTest extends AbstractIntegrationTest {
    @Autowired
    ProjectSynchronizer projectSynchronizer;

    @SneakyThrows
    @Test
    void name() {
        disableSSL();
        projectSynchronizer.test();
    }
}