package eu.venthe.pipeline.orchestrator.projects.application;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProjectsQueryService {
    Collection<ProjectDto> listProjects();

    Optional<ProjectDto> find(String systemId, String projectName);

    Optional<WorkflowDetailDto> showWorkflowDetail(String systemId);

    List<WorkflowTaskDto> showAllTasks();
}
