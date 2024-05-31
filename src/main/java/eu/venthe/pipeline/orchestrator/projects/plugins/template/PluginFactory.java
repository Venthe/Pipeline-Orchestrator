package eu.venthe.pipeline.orchestrator.projects.plugins.template;

import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.projects.plugins.gerrit.GerritProjectSourcePlugin;

public class PluginFactory {
    public ProjectSourcePlugin.PluginInstance instantiate(SourceType sourceType, SuppliedProperties suppliedProperties) {
        GerritProjectSourcePlugin gerritProjectSourcePlugin = new GerritProjectSourcePlugin();

        if (!sourceType.equals(gerritProjectSourcePlugin.getSourceType())) {
            throw new UnsupportedOperationException();
        }

        return gerritProjectSourcePlugin.instantiate(suppliedProperties);
    }
}
