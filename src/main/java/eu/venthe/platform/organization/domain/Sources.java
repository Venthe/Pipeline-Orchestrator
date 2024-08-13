package eu.venthe.platform.organization.domain;

import com.google.common.collect.MoreCollectors;
import eu.venthe.platform.organization.domain.source_configuration.plugins.PluginProvider;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.ProjectSourcePluginInstance;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.SourceType;
import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;

import java.util.HashMap;
import java.util.Map;

public class Sources {
    private final PluginProvider provider;
    private final Map<InternalSourceId, SourceWrapper> sources = new HashMap<>();

    Sources(final PluginProvider provider) {
        this.provider = provider;
    }

    public void addConfiguration(SourceType sourceType, SuppliedProperties suppliedProperties) {
        addConfiguration(sourceType, suppliedProperties, new SourceAlias("DEFAULT"));
    }

    public void addConfiguration(SourceType sourceType, SuppliedProperties suppliedProperties, SourceAlias alias) {
        sources.put(new InternalSourceId(), new SourceWrapper(provider.provide(sourceType, suppliedProperties), alias));
    }

    ProjectSourcePluginInstance getByAlias(final SourceAlias alias) {
        return sources.values().stream()
                .filter(source -> source.alias().equals(alias))
                .map(SourceWrapper::instance)
                .collect(MoreCollectors.onlyElement());
    }

    private record SourceWrapper(ProjectSourcePluginInstance instance, SourceAlias alias) {
    }

    public record SourceAlias(String value) {
        public static final SourceAlias DEFAULT = new SourceAlias("DEFAULT");
    }
}
