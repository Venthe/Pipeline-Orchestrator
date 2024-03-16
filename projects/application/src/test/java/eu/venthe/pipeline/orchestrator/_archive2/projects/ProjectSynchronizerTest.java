package eu.venthe.pipeline.orchestrator._archive2.projects;

import eu.venthe.pipeline.orchestrator.AbstractIntegrationTest;
import eu.venthe.pipeline.orchestrator.projects.domain.application.WorkflowTriggerService;
import eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.EventHandlerProvider;
import eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.handlers.WorkflowDispatchEventHandler;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@MockBean(classes = {WorkflowTriggerService.class, EventHandlerProvider.class, WorkflowDispatchEventHandler.class})
class ProjectSynchronizerTest extends AbstractIntegrationTest {
    @Autowired
    ProjectSynchronizer projectSynchronizer;

    @SneakyThrows
    @Test
    void name() {
        projectSynchronizer.test();
    }
}
