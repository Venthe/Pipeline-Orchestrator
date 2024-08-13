package eu.venthe.platform.source_configuration.domain;

import eu.venthe.platform.shared_kernel.Aggregate;
import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.source_configuration.domain.plugins.PluginProvider;
import eu.venthe.platform.source_configuration.domain.plugins.template.ProjectDataProvider;
import eu.venthe.platform.source_configuration.domain.plugins.template.ProjectSourcePluginInstance;
import eu.venthe.platform.source_configuration.domain.plugins.template.ProjectsProvider;
import eu.venthe.platform.source_configuration.domain.plugins.template.model.ProjectDto;
import eu.venthe.platform.source_configuration.domain.plugins.template.model.SourceType;
import lombok.*;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProjectsSourceConfiguration implements Aggregate<SourceConfigurationId>, ProjectsProvider, ProjectDataProvider {
    @EqualsAndHashCode.Include
    private final SourceConfigurationId configurationId;
    private final ProjectSourcePluginInstance pluginInstance;

    public static ProjectsSourceConfiguration create(Specification specification, PluginProvider pluginProvider) {
        return new ProjectsSourceConfiguration(specification.id(), pluginProvider.provide(specification.sourceType(), specification.properties()))
    }

    public SourceConfigurationId getId() {
        return configurationId;
    }

    @Override
    public Stream<ProjectDto> getProjects() {
        return Stream.empty();
    }

    @Override
    public Stream<ProjectDto.Id> getProjectIds() {
        return Stream.empty();
    }

    @Override
    public Optional<ProjectDto> getProject(final String id) {
        return Optional.empty();
    }

    @Override
    public Optional<File> getFile(final String projectIdentifier, final String revision, final Path path) {
        return pluginInstance.getFile(projectIdentifier, revision, path);
    }

    @Override
    public Collection<Metadata> getFileList(final String projectIdentifier, final String revision, final Path path) {
        return pluginInstance.getFileList(projectIdentifier, revision, path);
    }

    @Builder
    public record Specification(SourceConfigurationId id, SourceType sourceType, SuppliedProperties properties) {

        public static class SpecificationBuilder {
            private SuppliedProperties properties = SuppliedProperties.none();

            public ProjectsSourceConfiguration.Specification.SpecificationBuilder sourceType(String sourceType) {
                this.sourceType = new SourceType(sourceType);
                return this;
            }

            public ProjectsSourceConfiguration.Specification.SpecificationBuilder configurationId(String configurationId) {
                this.id = new SourceConfigurationId(configurationId);
                return this;
            }
        }
    }
}
