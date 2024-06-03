package eu.venthe.pipeline.orchestrator.projects.projects.application;

import eu.venthe.pipeline.orchestrator.projects.projects.api.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.projects.api.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.projects.api.WorkflowTaskDto;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.events.handlers.EventHandlerProvider;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.infrastructure.ProjectRepository;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.infrastructure.ProjectsSourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProjectsService implements ProjectsQueryService, ProjectsCommandService {
    private final ProjectRepository projectRepository;
    private final ProjectsSourceConfigurationRepository configurationRepository;
    private final DomainMessageBroker messageBroker;
    private final EventHandlerProvider eventHandlerProvider;

    @Override
    public Collection<ProjectDto> listProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectsService::toProjectDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<ProjectDto> find(ProjectId projectId) {
        return projectRepository.find(projectId).map(ProjectsService::toProjectDto);
    }

    @Override
    public List<WorkflowTaskDto> showAllTasks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<ProjectId> getProjectIds(ProjectsSourceConfigurationId configurationId) {
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

    @Override
    public void add(ProjectsSourceConfigurationId configurationId, CreateProjectSpecificationDto newProjectDto) {
        if (projectRepository.exists(newProjectDto.projectId())) {
            throw new IllegalArgumentException();
        }

        ProjectsSourceConfiguration configuration = configurationRepository.find(configurationId).orElseThrow();

        Project project = new Project(newProjectDto.projectId(), configuration, newProjectDto.description(), newProjectDto.status());

        projectRepository.save(project);
    }

    @Override
    public String executeManualWorkflow(ProjectId projectId, String ref, File workflowFile) {
        Project project = projectRepository.find(projectId).orElseThrow();

        project.executeManualWorkflow(ref, workflowFile);

        throw new UnsupportedOperationException();
    }
}
