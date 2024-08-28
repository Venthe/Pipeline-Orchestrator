package eu.venthe.platform.source_configuration.domain;

import eu.venthe.platform.shared_kernel.Aggregate;
import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.platform.shared_kernel.git.RevisionShortName;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.source_configuration.domain.model.*;
import eu.venthe.platform.source_configuration.domain.plugins.template.Repository;
import eu.venthe.platform.source_configuration.domain.plugins.template.RepositoryDataProvider;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceRepositoryName;
import eu.venthe.platform.source_configuration.domain.plugins.template.RepositorySourcePluginInstance;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class RepositorySourcePluginInstanceAggregate implements Aggregate<ConfigurationSourceId>, RepositoryDataProvider {
    @Getter
    @EqualsAndHashCode.Include
    private final ConfigurationSourceId id;
    private final SuppliedProperties properties;
    private final RepositorySourcePluginInstance pluginInstance;

    public SourceType getSourceType() {
        return pluginInstance.getSourceType();
    }

    @Override
    public Optional<File> getFile(final SourceRepositoryName sourceRepositoryName, final RevisionShortName simpleRevision, final Path path) {
        return pluginInstance.getFile(sourceRepositoryName, simpleRevision, path);
    }

    @Override
    public Collection<Metadata> getFileList(final SourceRepositoryName sourceRepositoryName, final RevisionShortName simpleRevision, final Path path) {
        return pluginInstance.getFileList(sourceRepositoryName, simpleRevision, path);
    }

    public Stream<SourceOwnedRepository> getRepository() {
        return pluginInstance.getRepository().map(this::toSourceOwnedRepository);
    }

    public Stream<SourceOwnedRepositoryName> getRepositoryNameentifiers() {
        return pluginInstance.getRepositoryNameentifiers().map(this::toSourceOwnedRepositoryName);
    }

    public Optional<SourceOwnedRepository> getRepository(final SourceRepositoryName id) {
        return pluginInstance.getRepository(id).map(this::toSourceOwnedRepository);
    }

    private SourceOwnedRepository toSourceOwnedRepository(final Repository e) {
        return new SourceOwnedRepository(toSourceOwnedRepositoryName(e.sourceRepositoryName()), e);
    }

    private SourceOwnedRepositoryName toSourceOwnedRepositoryName(final SourceRepositoryName e) {
        return new SourceOwnedRepositoryName(id, e);
    }
}
