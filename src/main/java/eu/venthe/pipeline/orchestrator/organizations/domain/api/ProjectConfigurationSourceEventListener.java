/*
package eu.venthe.pipeline.orchestrator.projects._archive.api;

import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.MessageListenerRegistry;
import eu.venthe.pipeline.orchestrator.projects._archive.api.events.ProjectSourceConfigurationAddedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProjectConfigurationSourceEventListener {
    private final ProjectsSourceConfigurationCommandService projectsSourceConfigurationService;

    public ProjectConfigurationSourceEventListener(MessageListenerRegistry listener, ProjectsSourceConfigurationCommandService projectsSourceConfigurationService) {
        this.projectsSourceConfigurationService = projectsSourceConfigurationService;

        listener.observe(ProjectSourceConfigurationAddedEvent.class, (envelope -> projectSourceAdded(envelope.getData())));
    }

    public void projectSourceAdded(ProjectSourceConfigurationAddedEvent event) {
        log.info("Received ProjectSourceConfigurationAddedEvent. {}", event);
        projectsSourceConfigurationService.synchronizeProject(event.getSourceId());
    }
}
*/
