package eu.venthe.pipeline.orchestrator.projects.projects.application;

import eu.venthe.pipeline.orchestrator.projects.projects.api.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.projects.api.WorkflowDetailDto;
import eu.venthe.pipeline.orchestrator.projects.projects.api.WorkflowTaskDto;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.ProjectsSourceConfigurationId;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectsQueryService {
    Collection<ProjectDto> listProjects();

    Optional<ProjectDto> find(String systemId, String projectName);

    Optional<WorkflowDetailDto> showWorkflowDetail(String systemId);

    List<WorkflowTaskDto> showAllTasks();

    Stream<ProjectId> getProjectIds(ProjectsSourceConfigurationId configurationId);
}
