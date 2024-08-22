package eu.venthe.platform.source_configuration.domain.plugins.template;

import eu.venthe.platform.source_configuration.domain.model.SourceType;

public interface RepositorySourcePluginInstance extends RepositoryProvider, RepositoryDataProvider {
    SourceType getSourceType();
}
