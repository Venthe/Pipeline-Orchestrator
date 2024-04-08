package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.api.CreateProjectSpecification;
import eu.venthe.pipeline.orchestrator.projects.api.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.api.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.api.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectRepository;
import eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.EventHandlerProvider;
import eu.venthe.pipeline.orchestrator.projects.shared_kernel.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.ProjectEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsQueryService, ProjectsCommandService {
    private final ProjectRepository projectRepository;
    private final DomainMessageBroker messageBroker;
    private final EventHandlerProvider eventHandlerProvider;

    @Override
    public Collection<ProjectDto> listProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectsServiceImpl::toProjectDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<ProjectDto> find(String systemId, String projectName) {
        return projectRepository.find(ProjectId.of(systemId, projectName)).map(ProjectsServiceImpl::toProjectDto);
    }

    @Override
    // FIXME: change project ID to repository from event
    public void handleEvent(String projectId, ProjectEvent event) {
        Project project = projectRepository.find(ProjectId.from(projectId)).orElseThrow();

        Collection<DomainEvent> domainEvents = project.handleEvent(event).apply(eventHandlerProvider);

        projectRepository.save(project);
        messageBroker.publish(domainEvents);
    }

    private static ProjectDto toProjectDto(Project p) {
        return new ProjectDto(p.getId().getId(), p.getId().getSystemId());
    }

    public void add(CreateProjectSpecification newProjectDto) {
        projectRepository.save(new Project(ProjectId.of(newProjectDto.getSystemId(), newProjectDto.getSystemId())));
    }
}
