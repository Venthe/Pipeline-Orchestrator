package eu.venthe.platform.source_configuration.domain.plugins.gerrit;

import eu.venthe.platform.source_configuration.domain.plugins.template.ProjectSourcePlugin;
import eu.venthe.platform.source_configuration.domain.plugins.template.ProjectSourcePluginInstance;
import eu.venthe.platform.source_configuration.domain.model.SourceType;
import eu.venthe.platform.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.platform.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GerritProjectSourcePlugin implements ProjectSourcePlugin {
    public static SourceType SOURCE_TYPE = new SourceType("Gerrit");

    @Override
    public SourceType getSourceType() {
        return SOURCE_TYPE;
    }

    @Override
    public ProjectSourcePluginInstance instantiate(SuppliedProperties properties) {
        return new GerritPluginInstance(GerritConfiguration.construct(properties));
    }

    @Override
    public Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions() {
        return GerritConfiguration.DEFINITIONS;
    }
}
