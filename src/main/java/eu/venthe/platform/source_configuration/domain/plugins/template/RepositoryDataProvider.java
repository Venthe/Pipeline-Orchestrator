package eu.venthe.platform.source_configuration.domain.plugins.template;

import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;

/**
 * Gives access to the data from the repository
 */
public interface RepositoryDataProvider {
    /**
     * @param repositoryIdentifier Unique identifier of the repository
     * @param simpleRevision          Revision identifier for the versioned file
     * @param path              Path of the file to access
     * @return Either {@code Optional} or bytes for a file
     */
    default Optional<File> getFile(SourceRepositoryId sourceRepositoryId, SimpleRevision simpleRevision, Path path) {
        throw new UnsupportedOperationException();
    }

    default Collection<Metadata> getFileList(SourceRepositoryId sourceRepositoryId, SimpleRevision simpleRevision, Path path) {
        throw new UnsupportedOperationException();
    }

}
