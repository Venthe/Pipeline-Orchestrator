package eu.venthe.pipeline.orchestrator.organizations.application;

import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.projects._archive.api.ProjectDto;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.projects._archive.api.WorkflowDetailDto;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.projects._archive.api.WorkflowTaskDto;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectId;

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

    Stream<ProjectId> getProjectIds(ProjectsSourceConfigurationId id);
}
