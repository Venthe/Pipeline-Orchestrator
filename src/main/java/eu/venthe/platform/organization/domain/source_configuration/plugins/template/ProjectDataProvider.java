package eu.venthe.platform.organization.domain.source_configuration.plugins.template;

import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.ProjectId;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.Revision;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;

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
    default Optional<File> getFile(ProjectId projectIdentifier, Revision revision, Path path) {
        throw new UnsupportedOperationException();
    }

    default Collection<Metadata> getFileList(ProjectId projectIdentifier, Revision revision, Path path) {
        throw new UnsupportedOperationException();
    }

}
