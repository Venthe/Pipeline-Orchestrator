/*
package eu.venthe.pipeline.orchestrator.projects.api;

import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.MessageListenerRegistry;
import eu.venthe.pipeline.orchestrator.projects_source.api.events.ProjectDiscoveredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProjectEventListener {
    private final ProjectsCommandService projectsService;

    public ProjectEventListener(MessageListenerRegistry listener, ProjectsCommandService projectsService) {
        this.projectsService = projectsService;

        listener.observe(ProjectDiscoveredEvent.class, (envelope -> projectAdded(envelope.getData())));
    }

    public void projectAdded(ProjectDiscoveredEvent event) {
        log.info("Received ProjectSourceConfigurationAddedEvent. {}", event);
        projectsService.add(new CreateProjectSpecification(new ProjectId(event.getProjectName(), event.getSystemId())));
    }

}
*/
