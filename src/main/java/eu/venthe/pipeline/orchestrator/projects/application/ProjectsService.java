package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.api.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.api.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.api.WorkflowTaskDto;
import eu.venthe.pipeline.orchestrator.projects.domain.events.handlers.EventHandlerProvider;
import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.JobExecutorCommandService;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.infrastructure.ProjectRepository;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProjectsService implements ProjectsQueryService, ProjectsCommandService {
    private final ProjectRepository projectRepository;
    private final SourceConfigurationRepository configurationRepository;
    private final DomainMessageBroker messageBroker;
    private final EventHandlerProvider eventHandlerProvider;
    private final JobExecutorCommandService jobExecutorCommandService;

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

        Project project = new Project(newProjectDto.projectId(), configuration, jobExecutorCommandService, newProjectDto.description(), newProjectDto.status());

        projectRepository.save(project);
    }

    @Override
    public ExecutionId executeManualWorkflow(ProjectId projectId, String ref, File workflowFile) {
        Project project = projectRepository.find(projectId).orElseThrow();

        return project.executeManualWorkflow(ref, workflowFile);
    }
}
