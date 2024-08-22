package eu.venthe.platform.source_configuration.application;

import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepository;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepositoryId;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceRepositoryId;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface SourceQueryService {
    Optional<File> getFile(ConfigurationSourceId configurationSourceId, SourceRepositoryId sourceRepositoryId, SimpleRevision simpleRevision, Path path);

    Collection<Metadata> getFileList(ConfigurationSourceId configurationSourceId, SourceRepositoryId SourceRepositoryId, SimpleRevision simpleRevision, Path path);

    Stream<SourceOwnedRepository> getRepository(ConfigurationSourceId configurationSourceId);

    Stream<SourceOwnedRepositoryId> getRepositoryIdentifiers(ConfigurationSourceId configurationSourceId);

    Optional<SourceOwnedRepository> getRepository(ConfigurationSourceId configurationSourceId, SourceRepositoryId id);
}
