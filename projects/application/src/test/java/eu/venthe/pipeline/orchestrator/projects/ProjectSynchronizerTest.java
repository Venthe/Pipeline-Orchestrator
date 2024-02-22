package eu.venthe.pipeline.orchestrator.projects;

import eu.venthe.pipeline.orchestrator.AbstractIntegrationTest;
import eu.venthe.pipeline.orchestrator.application.WorkflowTriggerService;
import eu.venthe.pipeline.orchestrator.event_handlers.EventHandlerProvider;
import eu.venthe.pipeline.orchestrator.event_handlers.handlers.WorkflowDispatchEventHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@MockBean(classes = {WorkflowTriggerService.class, EventHandlerProvider.class, WorkflowDispatchEventHandler.class})
class ProjectSynchronizerTest extends AbstractIntegrationTest {
    @Autowired
    ProjectSynchronizer projectSynchronizer;

    @Test
    void name() {
        projectSynchronizer.test();
    }
}