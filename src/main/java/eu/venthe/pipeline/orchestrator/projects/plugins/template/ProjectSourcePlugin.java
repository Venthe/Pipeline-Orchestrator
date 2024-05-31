package eu.venthe.pipeline.orchestrator.projects.plugins.template;

import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.PropertyName;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.SuppliedProperties;

import java.util.Map;

public interface ProjectSourcePlugin {
    SourceType getSourceType();

    PluginInstance instantiate(SuppliedProperties properties);

    Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions();

    interface PluginInstance extends ProjectsProvider, ProjectDataProvider, EndpointProvider {
        SourceType getSourceType();
    }

}
