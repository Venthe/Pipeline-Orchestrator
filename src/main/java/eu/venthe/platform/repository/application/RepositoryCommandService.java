package eu.venthe.platform.repository.application;

import eu.venthe.platform.repository.application.model.CreateRepositorySpecification;
import eu.venthe.platform.repository.domain.RepositoryId;
import eu.venthe.platform.shared_kernel.git.GitRevision;
import eu.venthe.platform.shared_kernel.repository.RepositoryStatus;

public interface RepositoryCommandService {

    void add(CreateRepositorySpecification createRepositorySpecification);

    default void synchronize(RepositoryName repositoryId) {
        throw new UnsupportedOperationException();
    }

    default void changeStatus(RepositoryName repositoryId, RepositoryStatus status) {
        throw new UnsupportedOperationException();
    }

    default void registerTrackedRevision(RepositoryName repositoryId, GitRevision revision) {
        throw new UnsupportedOperationException();
    }

    default void unregisterTrackedRevision(RepositoryName repositoryId, GitRevision revision) {
        throw new UnsupportedOperationException();
    }
}
