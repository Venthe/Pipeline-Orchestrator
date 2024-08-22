package eu.venthe.platform.source_configuration.application;

import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.source_configuration.domain.RepositorySourcePluginInstanceAggregate;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;
import eu.venthe.platform.source_configuration.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepository;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepositoryId;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceRepositoryId;
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

    public ConfigurationSourceId register(SourceConfigurationSpecification specification) {

        var configuration = new RepositorySourcePluginInstanceAggregate(new ConfigurationSourceId(), specification.properties(), provider.provide(specification.sourceType(), specification.properties()));
        repository.save(configuration);

        return configuration.getId();
    }

    public void unregister(ConfigurationSourceId sourceConfigurationId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<File> getFile(final ConfigurationSourceId configurationSourceId, final SourceRepositoryId sourceRepositoryId, final SimpleRevision simpleRevision, final Path path) {
        var sourceConfiguration = getConfiguration(configurationSourceId);
        return sourceConfiguration.getFile(sourceRepositoryId, simpleRevision, path);
    }

    @Override
    public Collection<Metadata> getFileList(final ConfigurationSourceId configurationSourceId, final SourceRepositoryId sourceRepositoryId, final SimpleRevision simpleRevision, final Path path) {
        var sourceConfiguration = getConfiguration(configurationSourceId);
        return sourceConfiguration.getFileList(sourceRepositoryId, simpleRevision, path);
    }

    @Override
    public Stream<SourceOwnedRepository> getRepository(final ConfigurationSourceId configurationSourceId) {
        var sourceConfiguration = getConfiguration(configurationSourceId);
        return sourceConfiguration.getRepository();
    }

    @Override
    public Stream<SourceOwnedRepositoryId> getRepositoryIdentifiers(final ConfigurationSourceId configurationSourceId) {
        var sourceConfiguration = getConfiguration(configurationSourceId);
        return sourceConfiguration.getRepositoryIdentifiers();
    }

    @Override
    public Optional<SourceOwnedRepository> getRepository(final ConfigurationSourceId configurationSourceId, final SourceRepositoryId id) {
        var sourceConfiguration = getConfiguration(configurationSourceId);
        return sourceConfiguration.getRepository(id);
    }

    private RepositorySourcePluginInstanceAggregate getConfiguration(final ConfigurationSourceId configurationSourceId) {
        return repository.find(configurationSourceId).orElseThrow();
    }

}
