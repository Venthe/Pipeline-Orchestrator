package eu.venthe.platform.repository.application;

import eu.venthe.platform.repository.application.model.CreateRepositorySpecification;
import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.shared_kernel.git.RevisionName;
import eu.venthe.platform.shared_kernel.project.RepositoryStatus;

public interface RepositoryCommandService {

    void add(CreateRepositorySpecification createRepositorySpecification);

    default void synchronize(RepositoryName repositoryId) {
        throw new UnsupportedOperationException();
    }

    default void changeStatus(RepositoryName repositoryId, RepositoryStatus status) {
        throw new UnsupportedOperationException();
    }

    default void registerTrackedRevision(RepositoryName repositoryId, RevisionName revision) {
        throw new UnsupportedOperationException();
    }

    default void unregisterTrackedRevision(RepositoryName repositoryId, RevisionName revision) {
        throw new UnsupportedOperationException();
    }
}
