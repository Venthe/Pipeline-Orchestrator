package eu.venthe.platform.data_source_configuration.domain.plugins.template;

import eu.venthe.platform.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.platform.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.platform.data_source_configuration.domain.plugins.template.model.SourceType;
import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;

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
