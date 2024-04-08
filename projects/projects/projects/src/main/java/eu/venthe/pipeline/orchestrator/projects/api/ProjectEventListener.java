package eu.venthe.pipeline.orchestrator.projects.api;

import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.MessageListenerRegistry;
import eu.venthe.pipeline.orchestrator.projects_source.api.events.ProjectDiscoveredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProjectEventListener {
    private final ProjectsCommandService projectsService;
    private final ProjectsQueryService queryService;

    public ProjectEventListener(MessageListenerRegistry listener, ProjectsCommandService projectsService, ProjectsQueryService queryService) {
        this.projectsService = projectsService;
        this.queryService = queryService;

        listener.observe(ProjectDiscoveredEvent.class, (envelope -> projectDiscovered(envelope.getData())));
    }

    public void projectDiscovered(ProjectDiscoveredEvent event) {
        log.info("Received ProjectSourceConfigurationAddedEvent. {}", event);

        if (queryService.find(event.getSystemId(), event.getProjectName()).isEmpty()) {
            projectsService.add(new CreateProjectSpecification(event.getProjectName(), event.getSystemId()));
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
