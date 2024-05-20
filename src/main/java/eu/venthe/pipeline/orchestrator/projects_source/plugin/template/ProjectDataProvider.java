package eu.venthe.pipeline.orchestrator.projects_source.plugin.template;

import java.nio.file.Path;
import java.util.Optional;

/**
 * Gives access to the data from the project
 */
public interface ProjectDataProvider {
    /**
     * @param projectIdentifier Unique identifier of the project
     * @param revision Revision identifier for the versioned file
     * @param path Path of the file to access
     * @return Either {@code Optional} or bytes for a file
     */
    Optional<byte[]> getFile(String projectIdentifier, String revision, Path path);
}
