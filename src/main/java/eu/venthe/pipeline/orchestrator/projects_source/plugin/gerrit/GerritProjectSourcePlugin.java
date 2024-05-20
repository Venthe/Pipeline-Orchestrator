package eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit;

import eu.venthe.pipeline.orchestrator.projects_source.domain.SourceType;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.PropertyName;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.SuppliedProperties;

import java.util.Map;

public class GerritProjectSourcePlugin implements ProjectSourcePlugin {

    @Override
    public SourceType getSourceType() {
        return new SourceType("Gerrit");
    }

    @Override
    public PluginInstance instantiate(SuppliedProperties properties) {
        return new GerritPluginInstance(GerritConfiguration.construct(properties));
    }

    @Override
    public Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions() {
        return GerritConfiguration.DEFINITIONS;
    }
}
