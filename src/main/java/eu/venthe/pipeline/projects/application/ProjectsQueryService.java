package eu.venthe.pipeline.projects.application;

import eu.venthe.pipeline.projects.application.dto.WorkflowTaskDto;
import eu.venthe.pipeline.projects.application.dto.ProjectDto;
import eu.venthe.pipeline.projects.application.dto.WorkflowDetailDto;
import eu.venthe.pipeline.projects.application.dto.ProjectDetailsDto;
import eu.venthe.pipeline.projects.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.projects.domain.ProjectId;
import eu.venthe.pipeline.workflows.utilities.GlobPatternMatching;
import eu.venthe.pipeline.shared_kernel.git.GitRevision;
import eu.venthe.pipeline.shared_kernel.io.File;

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

    Optional<File> getFile(ProjectId id, GitRevision revision, Path file);

    Optional<ProjectDetailsDto> getProjectDetails(ProjectId id);
}
