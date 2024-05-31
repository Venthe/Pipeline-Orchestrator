package eu.venthe.pipeline.orchestrator.projects.plugins.gerrit;

import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.PropertyName;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.SuppliedProperties;

import java.util.Map;

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
