package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.api.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.api.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.api.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsQueryService, ProjectsCommandService {
    private final ProjectRepository projectRepository;

    @Override
    public Collection<ProjectDto> listProjects() {
        return projectRepository.findAll().stream()
                .map(p -> new ProjectDto(p.getId().getId(), p.getId().getSystemId()))
                .collect(Collectors.toSet());
    }
}
