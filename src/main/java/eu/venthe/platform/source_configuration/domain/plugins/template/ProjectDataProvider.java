package eu.venthe.platform.source_configuration.domain.plugins.template;

import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.shared_kernel.git.Revision;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;

/**
 * Gives access to the data from the project
 */
public interface ProjectDataProvider {
    /**
     * @param projectIdentifier Unique identifier of the project
     * @param revision          Revision identifier for the versioned file
     * @param path              Path of the file to access
     * @return Either {@code Optional} or bytes for a file
     */
    default Optional<File> getFile(SourceProjectId sourceProjectId, Revision revision, Path path) {
        throw new UnsupportedOperationException();
    }

    default Collection<Metadata> getFileList(SourceProjectId sourceProjectId, Revision revision, Path path) {
        throw new UnsupportedOperationException();
    }

}
