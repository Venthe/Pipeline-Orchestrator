package eu.venthe.platform.organization.domain.source_configuration.plugins.template;

import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.SourceType;

public interface ProjectSourcePluginInstance extends ProjectsProvider, ProjectDataProvider {
    SourceType getSourceType();
}
