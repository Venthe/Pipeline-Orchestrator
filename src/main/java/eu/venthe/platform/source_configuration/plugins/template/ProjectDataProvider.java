package eu.venthe.platform.source_configuration.plugins.template;

import eu.venthe.platform.source_configuration.plugins.template.model.FileDto;

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
    default Optional<byte[]> getFile(String projectIdentifier, String revision, Path path) {
        throw new UnsupportedOperationException();
    }

    default Collection<FileDto> getFileList(String projectIdentifier, String revision, Path path) {
        throw new UnsupportedOperationException();
    }

}
