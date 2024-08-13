package eu.venthe.platform.source_configuration.application;

import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.platform.source_configuration.domain.SourceConfigurationId;
import eu.venthe.platform.source_configuration.domain.infrastructure.DataSourceConfigurationRepository;
import eu.venthe.platform.source_configuration.domain.plugins.template.model.ProjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
@Service
public class DataSourceQueryService {
    private final DataSourceConfigurationRepository repository;

    public Stream<ProjectDto> getProjects(SourceConfigurationId sourceConfigurationId) {
        return getConfiguration(sourceConfigurationId).getProjects();
    }

    public Stream<ProjectDto.Id> getProjectIds(SourceConfigurationId sourceConfigurationId) {
        return getConfiguration(sourceConfigurationId).getProjectIds();
    }

    public Optional<ProjectDto> getProject(SourceConfigurationId sourceConfigurationId, final String projectIdentifier) {
        return getConfiguration(sourceConfigurationId).getProject(projectIdentifier);
    }

    public Optional<File> getFile(SourceConfigurationId sourceConfigurationId, final String projectIdentifier, final String revision, final Path path) {
        return getConfiguration(sourceConfigurationId).getFile(projectIdentifier, revision, path);
    }

    public Collection<Metadata> getFileList(SourceConfigurationId sourceConfigurationId, String projectIdentifier, final String revision, final Path path) {
        return getConfiguration(sourceConfigurationId).getFileList(projectIdentifier, revision, path);
    }

    private ProjectsSourceConfiguration getConfiguration(final SourceConfigurationId sourceConfigurationId) {
        return repository.find(sourceConfigurationId).orElseThrow();
    }
}
