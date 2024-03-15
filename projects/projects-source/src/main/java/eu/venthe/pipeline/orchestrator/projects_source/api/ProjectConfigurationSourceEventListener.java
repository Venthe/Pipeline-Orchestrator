package eu.venthe.pipeline.orchestrator.projects_source.api;

import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.MessageListenerRegistry;
import eu.venthe.pipeline.orchestrator.projects_source.application.ProjectsSourceConfigurationService;
import eu.venthe.pipeline.orchestrator.projects_source.domain.ProjectSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects_source.domain.events.ProjectSourceConfigurationAddedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProjectConfigurationSourceEventListener {
    private final ProjectsSourceConfigurationService projectsSourceConfigurationService;

    public ProjectConfigurationSourceEventListener(MessageListenerRegistry listener, ProjectsSourceConfigurationService projectsSourceConfigurationService) {
        this.projectsSourceConfigurationService = projectsSourceConfigurationService;

        listener.observe(ProjectSourceConfigurationAddedEvent.class, (envelope -> projectSourceAdded(envelope.getData())));
    }

    public void projectSourceAdded(ProjectSourceConfigurationAddedEvent event) {
        log.info("Received ProjectSourceConfigurationAddedEvent. {}", event);
        projectsSourceConfigurationService.synchronizeProjects(ProjectSourceConfigurationId.of(event.getSourceId()));
    }
}
