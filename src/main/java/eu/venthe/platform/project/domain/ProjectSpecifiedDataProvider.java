package eu.venthe.platform.project.domain;

import eu.venthe.platform.project.domain.source_configurations.plugins.template.ProjectDataProvider;
import eu.venthe.platform.project.domain.source_configurations.plugins.template.model.FileDto;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;

@Slf4j
public record ProjectSpecifiedDataProvider(String projectName,
                                           ProjectDataProvider provider) {
    public Optional<byte[]> getFile(String revision, Path path) {
        return provider.getFile(projectName, revision, path);
    }

    public Collection<FileDto> getFileList(String revision, Path path) {
        return provider.getFileList(projectName, revision, path);
    }
}
