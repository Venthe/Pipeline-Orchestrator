package eu.venthe.pipeline.orchestrator.projects.application.impl;

import eu.venthe.pipeline.orchestrator.modules.ProjectModuleMediator;
import eu.venthe.pipeline.orchestrator.organizations.application.dto.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.organizations.application.dto.ProjectDto;
import eu.venthe.pipeline.orchestrator.organizations.application.dto.WorkflowTaskDto;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.infrastructure.ProjectRepository;
import eu.venthe.pipeline.orchestrator.projects.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProjectsQueryServiceImpl implements ProjectsQueryService {
    private final ProjectRepository projectRepository;

    @Override
    public Collection<ProjectDto> listProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectsQueryServiceImpl::toProjectDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<ProjectDto> find(ProjectId projectId) {
        return projectRepository.find(projectId).map(ProjectsQueryServiceImpl::toProjectDto);
    }

    @Override
    public List<WorkflowTaskDto> showAllTasks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<ProjectId> getProjectIds(SourceConfigurationId configurationId) {
        return projectRepository.findAll().stream()
                .filter(e -> e.getOwningConfiguration()
                        .getConfigurationId()
                        .equals(configurationId)
                )
                .map(Project::getId);
    }

    private static ProjectDto toProjectDto(Project p) {
        return new ProjectDto(p.getId().getName(), p.getId().getConfigurationId().id(), p.getStatus());
    }
}
