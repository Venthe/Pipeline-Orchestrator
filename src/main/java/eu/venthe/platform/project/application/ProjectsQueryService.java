package eu.venthe.platform.project.application;

import eu.venthe.platform.project.application.dto.WorkflowTaskDto;
import eu.venthe.platform.project.application.dto.ProjectDto;
import eu.venthe.platform.project.application.dto.WorkflowDetailDto;
import eu.venthe.platform.project.application.dto.ProjectDetailsDto;
import eu.venthe.platform.source_configuration.SourceConfigurationId;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.workflow.utilities.GlobPatternMatching;
import eu.venthe.platform.shared_kernel.git.GitRevision;
import eu.venthe.platform.shared_kernel.io.File;

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
