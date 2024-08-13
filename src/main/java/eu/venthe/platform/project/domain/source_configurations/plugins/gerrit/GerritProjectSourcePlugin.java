package eu.venthe.platform.project.domain.source_configurations.plugins.gerrit;

import eu.venthe.platform.project.domain.source_configurations.plugins.template.ProjectSourcePlugin;
import eu.venthe.platform.project.domain.source_configurations.plugins.template.model.SourceType;
import eu.venthe.platform.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.platform.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GerritProjectSourcePlugin implements ProjectSourcePlugin {

    @Override
    public SourceType getSourceType() {
        return new SourceType("Gerrit");
    }

    @Override
    public PluginInstance instantiate(SuppliedProperties properties) {
        return new GerritPluginInstance(GerritConfiguration.construct(properties), getSourceType());
    }

    @Override
    public Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions() {
        return GerritConfiguration.DEFINITIONS;
    }
}
