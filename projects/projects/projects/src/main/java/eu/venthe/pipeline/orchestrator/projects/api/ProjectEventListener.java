package eu.venthe.pipeline.orchestrator.projects.api;

import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.MessageListenerRegistry;
import eu.venthe.pipeline.orchestrator.projects.api.dto.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.api.dto.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.api.dto.UpdateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects_source.api.events.ProjectDiscoveredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        Optional<ProjectDto> projectDto = queryService.find(event.getSystemId(), event.getProjectName());
        if (projectDto.isEmpty()) {
            projectsService.add(new CreateProjectSpecificationDto(event.getProjectName(), event.getSystemId()));
        } else if (event.getStatus() != projectDto.get().getStatus() || !event.getProjectName().equalsIgnoreCase(projectDto.get().getName())) {
            projectsService.updateDetails(event.getSystemId(), new UpdateProjectSpecificationDto(event.getStatus(), event.getProjectName()));
        }
    }
}
