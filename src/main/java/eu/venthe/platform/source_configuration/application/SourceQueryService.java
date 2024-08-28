package eu.venthe.platform.source_configuration.application;

import eu.venthe.platform.shared_kernel.git.RevisionShortName;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepository;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepositoryName;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceRepositoryName;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface SourceQueryService {
    Optional<File> getFile(ConfigurationSourceId configurationSourceId, SourceRepositoryName sourceRepositoryName, RevisionShortName simpleRevision, Path path);

    Collection<Metadata> getFileList(ConfigurationSourceId configurationSourceId, SourceRepositoryName SourceRepositoryName, RevisionShortName simpleRevision, Path path);

    Stream<SourceOwnedRepository> getRepository(ConfigurationSourceId configurationSourceId);

    Stream<SourceOwnedRepositoryName> getRepositoryNameentifiers(ConfigurationSourceId configurationSourceId);

    Optional<SourceOwnedRepository> getRepository(ConfigurationSourceId configurationSourceId, SourceRepositoryName id);
}
