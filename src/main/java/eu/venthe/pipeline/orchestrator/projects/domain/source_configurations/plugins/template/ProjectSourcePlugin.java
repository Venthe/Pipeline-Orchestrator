package eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template;

import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

import java.util.Map;

public interface ProjectSourcePlugin {
    default void validateProperties(SuppliedProperties properties) {
        throw new UnsupportedOperationException();
    }

    SourceType getSourceType();

    PluginInstance instantiate(SuppliedProperties properties);

    Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions();

    interface PluginInstance extends ProjectsProvider, ProjectDataProvider, EndpointProvider {
        SourceType getSourceType();
    }

}
