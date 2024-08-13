package eu.venthe.platform.source_configuration.domain.plugins.template;

import eu.venthe.platform.source_configuration.domain.plugins.template.model.SourceType;

public interface ProjectSourcePluginInstance extends ProjectsProvider, ProjectDataProvider {
    SourceType getSourceType();
}
