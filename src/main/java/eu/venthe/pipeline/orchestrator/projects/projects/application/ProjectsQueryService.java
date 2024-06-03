package eu.venthe.pipeline.orchestrator.projects.projects.application;

import eu.venthe.pipeline.orchestrator.projects.projects.api.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.projects.api.WorkflowDetailDto;
import eu.venthe.pipeline.orchestrator.projects.projects.api.WorkflowTaskDto;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.model.ProjectsSourceConfigurationId;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectsQueryService {
    Collection<ProjectDto> listProjects();

    Optional<ProjectDto> find(ProjectId projectId);

    default Optional<WorkflowDetailDto> showWorkflowDetail(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    List<WorkflowTaskDto> showAllTasks();

    Stream<ProjectId> getProjectIds(ProjectsSourceConfigurationId configurationId);
}
