package eu.venthe.platform.source_configuration.application;

import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.source_configuration.ProjectSourcePluginInstanceAggregate;
import eu.venthe.platform.source_configuration.domain.model.SourceId;
import eu.venthe.platform.source_configuration.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProject;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProjectId;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceProjectId;
import eu.venthe.platform.source_configuration.domain.model.Revision;
import eu.venthe.platform.source_configuration.domain.plugins.PluginProvider;
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
public class SourceConfigurationManager implements SourceQueryService {
    private final SourceConfigurationRepository repository;
    private final PluginProvider provider;

    public SourceId register(SourceConfigurationSpecification specification) {

        var configuration = new ProjectSourcePluginInstanceAggregate(new SourceId(), specification.properties(), provider.provide(specification.sourceType(), specification.properties()));
        repository.save(configuration);

        return configuration.getId();
    }

    public void unregister(SourceId sourceConfigurationId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<File> getFile(final SourceId sourceId, final SourceProjectId sourceProjectId, final Revision revision, final Path path) {
        var sourceConfiguration = getConfiguration(sourceId);
        return sourceConfiguration.getFile(sourceProjectId, revision, path);
    }

    @Override
    public Collection<Metadata> getFileList(final SourceId sourceId, final SourceProjectId sourceProjectId, final Revision revision, final Path path) {
        var sourceConfiguration = getConfiguration(sourceId);
        return sourceConfiguration.getFileList(sourceProjectId, revision, path);
    }

    @Override
    public Stream<SourceOwnedProject> getProjects(final SourceId sourceId) {
        var sourceConfiguration = getConfiguration(sourceId);
        return sourceConfiguration.getProjects();
    }

    @Override
    public Stream<SourceOwnedProjectId> getProjectIdentifiers(final SourceId sourceId) {
        var sourceConfiguration = getConfiguration(sourceId);
        return sourceConfiguration.getProjectIdentifiers();
    }

    @Override
    public Optional<SourceOwnedProject> getProject(final SourceId sourceId, final SourceProjectId id) {
        var sourceConfiguration = getConfiguration(sourceId);
        return sourceConfiguration.getProject(id);
    }

    private ProjectSourcePluginInstanceAggregate getConfiguration(final SourceId sourceId) {
        return repository.find(sourceId).orElseThrow();
    }

}
