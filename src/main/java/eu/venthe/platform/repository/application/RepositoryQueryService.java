package eu.venthe.platform.repository.application;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.repository.application.model.RepositoryDto;
import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.shared_kernel.git.RevisionShortName;
import eu.venthe.platform.shared_kernel.io.File;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface RepositoryQueryService {
    Collection<RepositoryDto> listRepository();

    Optional<RepositoryDto> find(RepositoryName repositoryId);

    Stream<RepositoryName> getRepositoryIds(OrganizationName id);

    Optional<File> getFile(RepositoryName id, RevisionShortName revision, Path file);
}
