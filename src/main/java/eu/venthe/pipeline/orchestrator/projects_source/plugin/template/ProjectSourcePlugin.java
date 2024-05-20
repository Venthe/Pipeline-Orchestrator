package eu.venthe.pipeline.orchestrator.projects_source.plugin.template;

import eu.venthe.pipeline.orchestrator.projects_source.domain.SourceType;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.PropertyName;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.SuppliedProperties;

import java.util.Map;

public interface ProjectSourcePlugin {
    SourceType getSourceType();

    PluginInstance instantiate(SuppliedProperties properties);

    Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions();

    interface PluginInstance extends ProjectsProvider, ProjectDataProvider {
    }

}
