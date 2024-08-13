package eu.venthe.platform.organization.domain.source_configuration.plugins.template;

import eu.venthe.platform.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.platform.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.SourceType;
import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;

import java.util.Map;

public interface ProjectSourcePlugin {
    default void validateProperties(SuppliedProperties properties) {
        throw new UnsupportedOperationException();
    }

    SourceType getSourceType();

    ProjectSourcePluginInstance instantiate(SuppliedProperties properties);

    Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions();

}
