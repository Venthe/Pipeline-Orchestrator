package eu.venthe.platform.repository.application;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.repository.application.model.RepositoryDto;
import eu.venthe.platform.repository.domain.RepositoryId;
import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.shared_kernel.io.File;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface RepositoryQueryService {
    Collection<RepositoryDto> listRepository();

    Optional<RepositoryDto> find(RepositoryId repositoryId);

    Stream<RepositoryId> getRepositoryIds(OrganizationName id);

    Optional<File> getFile(RepositoryId id, SimpleRevision revision, Path file);
}
