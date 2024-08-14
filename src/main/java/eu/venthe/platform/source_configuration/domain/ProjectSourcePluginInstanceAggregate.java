package eu.venthe.platform.source_configuration.domain;

import eu.venthe.platform.shared_kernel.Aggregate;
import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.platform.shared_kernel.git.Revision;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.source_configuration.domain.model.*;
import eu.venthe.platform.source_configuration.domain.plugins.template.Project;
import eu.venthe.platform.source_configuration.domain.plugins.template.ProjectDataProvider;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceProjectId;
import eu.venthe.platform.source_configuration.domain.plugins.template.ProjectSourcePluginInstance;
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
public class ProjectSourcePluginInstanceAggregate implements Aggregate<ConfigurationSourceId>, ProjectDataProvider {
    @Getter
    @EqualsAndHashCode.Include
    private final ConfigurationSourceId id;
    private final SuppliedProperties properties;
    private final ProjectSourcePluginInstance pluginInstance;

    public SourceType getSourceType() {
        return pluginInstance.getSourceType();
    }

    @Override
    public Optional<File> getFile(final SourceProjectId sourceProjectId, final Revision revision, final Path path) {
        return pluginInstance.getFile(sourceProjectId, revision, path);
    }

    @Override
    public Collection<Metadata> getFileList(final SourceProjectId sourceProjectId, final Revision revision, final Path path) {
        return pluginInstance.getFileList(sourceProjectId, revision, path);
    }

    public Stream<SourceOwnedProject> getProjects() {
        return pluginInstance.getProjects().map(this::toSourceOwnedProject);
    }

    public Stream<SourceOwnedProjectId> getProjectIdentifiers() {
        return pluginInstance.getProjectIdentifiers().map(this::toSourceOwnedProjectId);
    }

    public Optional<SourceOwnedProject> getProject(final SourceProjectId id) {
        return pluginInstance.getProject(id).map(this::toSourceOwnedProject);
    }

    private SourceOwnedProject toSourceOwnedProject(final Project e) {
        return new SourceOwnedProject(toSourceOwnedProjectId(e.sourceProjectId()), e);
    }

    private SourceOwnedProjectId toSourceOwnedProjectId(final SourceProjectId e) {
        return new SourceOwnedProjectId(id, e);
    }
}
