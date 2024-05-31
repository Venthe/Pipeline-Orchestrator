package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.api.dto.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.api.dto.WorkflowDetailDto;
import eu.venthe.pipeline.orchestrator.projects.api.dto.WorkflowTaskDto;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configuration.ProjectsSourceConfigurationId;

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
