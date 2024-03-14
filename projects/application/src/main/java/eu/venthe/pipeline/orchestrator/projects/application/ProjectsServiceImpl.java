package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsService {
    private final FeatureManager featureManager;
    private final ProjectRepository projectRepository;

    @Override
    public Collection<ProjectDto> listProjects() {
        return projectRepository.findAll().stream()
                .map(p -> new ProjectDto(p.getId().id(), p.getId().systemId()))
                .collect(Collectors.toSet());
    }

    @Override
    public void addProject(NewProjectDto newProjectDto) {
        projectRepository.save(new Project(new Project.Id(newProjectDto.getId().id(), newProjectDto.getId().systemId())));
    }
}
