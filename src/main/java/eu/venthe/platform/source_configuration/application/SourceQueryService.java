package eu.venthe.platform.source_configuration.application;

import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProject;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProjectId;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceProjectId;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface SourceQueryService {
    Optional<File> getFile(ConfigurationSourceId configurationSourceId, SourceProjectId sourceProjectId, SimpleRevision simpleRevision, Path path);

    Collection<Metadata> getFileList(ConfigurationSourceId configurationSourceId, SourceProjectId SourceProjectId, SimpleRevision simpleRevision, Path path);

    Stream<SourceOwnedProject> getProjects(ConfigurationSourceId configurationSourceId);

    Stream<SourceOwnedProjectId> getProjectIdentifiers(ConfigurationSourceId configurationSourceId);

    Optional<SourceOwnedProject> getProject(ConfigurationSourceId configurationSourceId, SourceProjectId id);
}
