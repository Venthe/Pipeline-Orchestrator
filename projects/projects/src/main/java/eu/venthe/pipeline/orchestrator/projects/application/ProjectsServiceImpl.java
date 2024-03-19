package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.projects.api.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.api.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.api.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectRepository;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsQueryService, ProjectsCommandService {
    private final ProjectRepository projectRepository;
    private final DomainMessageBroker messageBroker;

    @Override
    public Collection<ProjectDto> listProjects() {
        return projectRepository.findAll().stream()
                .map(p -> new ProjectDto(p.getId().getId(), p.getId().getSystemId()))
                .collect(Collectors.toSet());
    }

    @Override
    public void handleEvent(String projectId, Event event) {
        Project project = projectRepository.find(ProjectId.from(projectId)).orElseThrow();

        Collection<DomainEvent> domainEvents = project.handleEvent(event);

        projectRepository.save(project);
        messageBroker.publish(domainEvents);
    }
}
