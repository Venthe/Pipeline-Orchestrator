package eu.venthe.platform.project.application;

import eu.venthe.platform.namespace.domain.NamespaceName;
import eu.venthe.platform.project.application.model.ProjectDto;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.shared_kernel.git.GitRevision;
import eu.venthe.platform.shared_kernel.io.File;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectsQueryService {
    Collection<ProjectDto> listProjects();

    Optional<ProjectDto> find(ProjectId projectId);

    Stream<ProjectId> getProjectIds(NamespaceName id);

    Optional<File> getFile(ProjectId id, GitRevision revision, Path file);
}
