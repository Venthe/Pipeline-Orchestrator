package eu.venthe.platform.project.application;

import eu.venthe.platform.project.application.model.CreateProjectSpecification;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.shared_kernel.git.GitRevision;
import eu.venthe.platform.shared_kernel.project.ProjectStatus;

public interface ProjectsCommandService {

    void add(CreateProjectSpecification createProjectSpecification);

    default void synchronize(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void changeStatus(ProjectId projectId, ProjectStatus status) {
        throw new UnsupportedOperationException();
    }

    default void registerTrackedRevision(ProjectId projectId, GitRevision revision) {
        throw new UnsupportedOperationException();
    }

    default void unregisterTrackedRevision(ProjectId projectId, GitRevision revision) {
        throw new UnsupportedOperationException();
    }
}
