package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.projects.api.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.projects.api.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.projects.api.WorkflowDetailDto;
import eu.venthe.pipeline.orchestrator.projects.projects.api.WorkflowTaskDto;
import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.events.handlers.EventHandlerProvider;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.infrastructure.ProjectRepository;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.infrastructure.ProjectsSourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsQueryService, ProjectsCommandService {
    private final ProjectRepository projectRepository;
    private final ProjectsSourceConfigurationRepository configurationRepository;
    private final DomainMessageBroker messageBroker;
    private final EventHandlerProvider eventHandlerProvider;

    @Override
    public Collection<ProjectDto> listProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectsServiceImpl::toProjectDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<ProjectDto> find(String configurationid, String projectName) {
        return projectRepository.find(ProjectId.of(new ProjectsSourceConfigurationId(configurationid), projectName)).map(ProjectsServiceImpl::toProjectDto);
    }

    @Override
    public Optional<WorkflowDetailDto> showWorkflowDetail(String systemId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<WorkflowTaskDto> showAllTasks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<ProjectId> getProjectIds(ProjectsSourceConfigurationId configurationId) {
        return Stream.empty();
    }

    private static ProjectDto toProjectDto(Project p) {
        return new ProjectDto(p.getId().getName(), p.getId().getConfigurationId().id(), p.getStatus());
    }

    @Override
    public void add(String _configurationId, CreateProjectSpecificationDto newProjectDto) {
        if (projectRepository.exists(newProjectDto.projectId())) {
            throw new IllegalArgumentException();
        }

        ProjectsSourceConfigurationId configurationId = new ProjectsSourceConfigurationId(_configurationId);
        ProjectsSourceConfiguration configuration = configurationRepository.find(configurationId).orElseThrow();

        Project project = new Project(newProjectDto.projectId(), configuration, newProjectDto.description(), newProjectDto.status());

        projectRepository.save(project);
    }
}
