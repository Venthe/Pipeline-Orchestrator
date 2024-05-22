package eu.venthe.pipeline.orchestrator.projects.plugin.template;

import eu.venthe.pipeline.orchestrator.projects.domain.model.SourceType;
import eu.venthe.pipeline.orchestrator.projects.plugin.template.model.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.projects.plugin.template.model.PropertyName;
import eu.venthe.pipeline.orchestrator.projects.plugin.template.model.SuppliedProperties;

import java.util.Map;

public interface ProjectSourcePlugin {
    SourceType getSourceType();

    PluginInstance instantiate(SuppliedProperties properties);

    Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions();

    interface PluginInstance extends ProjectsProvider, ProjectDataProvider, EndpointProvider {
        SourceType getSourceType();
    }

}
