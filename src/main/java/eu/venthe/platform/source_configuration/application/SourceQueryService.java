package eu.venthe.platform.source_configuration.application;

import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.source_configuration.domain.model.Revision;
import eu.venthe.platform.source_configuration.domain.model.SourceId;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProject;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProjectId;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceProjectId;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface SourceQueryService {
    Optional<File> getFile(SourceId sourceId, SourceProjectId sourceProjectId, Revision revision, Path path);

    Collection<Metadata> getFileList(SourceId sourceId, SourceProjectId SourceProjectId, Revision revision, Path path);

    Stream<SourceOwnedProject> getProjects(SourceId sourceId);

    Stream<SourceOwnedProjectId> getProjectIdentifiers(SourceId sourceId);

    Optional<SourceOwnedProject> getProject(SourceId sourceId, SourceProjectId id);
}
