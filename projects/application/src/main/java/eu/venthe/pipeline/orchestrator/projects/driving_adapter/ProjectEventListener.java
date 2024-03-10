package eu.venthe.pipeline.orchestrator.projects.driving_adapter;

import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.MessageListenerRegistry;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.events.ProjectDiscoveredEvent;
import eu.venthe.pipeline.orchestrator.projects.application.NewProjectDto;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProjectEventListener {
    private final ProjectsService projectsService;

    public ProjectEventListener(MessageListenerRegistry listener, ProjectsService projectsService) {
        this.projectsService = projectsService;

        listener.observe(ProjectDiscoveredEvent.class, (envelope -> projectAdded(envelope.getData())));
    }

    public void projectAdded(ProjectDiscoveredEvent event) {
        log.info("Received ProjectSourceConfigurationAddedEvent. {}", event);
        projectsService.addProject(new NewProjectDto());
    }

}
