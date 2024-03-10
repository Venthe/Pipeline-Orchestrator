package eu.venthe.pipeline.orchestrator.project_configuration_sources.driving_adapter;

import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.MessageListenerRegistry;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application.ProjectsSourceConfigurationService;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.ProjectSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.events.ProjectSourceConfigurationAddedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProjectConfigurationSourceEventListener {
    private final ProjectsSourceConfigurationService projectsSourceConfigurationService;

    public ProjectConfigurationSourceEventListener(MessageListenerRegistry listener, ProjectsSourceConfigurationService projectsSourceConfigurationService) {
        this.projectsSourceConfigurationService = projectsSourceConfigurationService;

        listener.observe((envelope -> projectSourceAdded((ProjectSourceConfigurationAddedEvent) envelope.getData())));
    }

    public void projectSourceAdded(ProjectSourceConfigurationAddedEvent event) {
        log.info("Received ProjectSourceConfigurationAddedEvent. {}", event);
        projectsSourceConfigurationService.synchronizeProjects(new ProjectSourceConfigurationId(event.getProjectSourceId()));
    }
}
