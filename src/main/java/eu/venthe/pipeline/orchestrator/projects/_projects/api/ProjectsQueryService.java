package eu.venthe.pipeline.orchestrator.projects._projects.api;

import eu.venthe.pipeline.orchestrator.projects._projects.api.dto.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects._projects.api.dto.WorkflowDetailDto;
import eu.venthe.pipeline.orchestrator.projects._projects.api.dto.WorkflowTaskDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProjectsQueryService {
    Collection<ProjectDto> listProjects();

    Optional<ProjectDto> find(String systemId, String projectName);

    Optional<WorkflowDetailDto> showWorkflowDetail(String systemId);

    List<WorkflowTaskDto> showAllTasks();
}
