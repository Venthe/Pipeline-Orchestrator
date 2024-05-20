package eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit;

import eu.venthe.pipeline.orchestrator.projects_source.domain.SourceType;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.ProjectSourcePluginInstance;
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
        final GerritConfiguration configuration = GerritConfiguration.construct(properties);

        GerritApiClient apiClient = new GerritApiClient(configuration);
        GerritProjectProvider projectProvider = new GerritProjectProvider(apiClient);
        GerritProjectDataProvider dataProvider = new GerritProjectDataProvider(apiClient);

        return new ProjectSourcePluginInstance(projectProvider, dataProvider);
    }

    @Override
    public Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions() {
        return GerritConfiguration.DEFINITIONS;
    }
}
