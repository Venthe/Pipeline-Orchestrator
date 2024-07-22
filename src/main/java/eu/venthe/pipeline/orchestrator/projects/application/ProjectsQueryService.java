package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.application.dto.WorkflowTaskDto;
import eu.venthe.pipeline.orchestrator.projects.application.dto.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.application.dto.WorkflowDetailDto;
import eu.venthe.pipeline.orchestrator.projects.application.dto.ProjectDetailsDto;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.utilities.GlobPatternMatching;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface ProjectsQueryService {
    Collection<ProjectDto> listProjects();

    Optional<ProjectDto> find(ProjectId projectId);

    default Optional<WorkflowDetailDto> showWorkflowDetail(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    List<WorkflowTaskDto> showAllTasks();

    Stream<ProjectId> getProjectIds(SourceConfigurationId id);

    Set<File> getFiles(GlobPatternMatching.Glob pattern);

    Optional<File> getFile(ProjectId id, Revision revision, Path file);

    Optional<ProjectDetailsDto> getProjectDetails(ProjectId id);
}
