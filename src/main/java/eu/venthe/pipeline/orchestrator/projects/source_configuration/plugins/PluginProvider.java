package eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins;

import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.gerrit.GerritProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.SuppliedProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PluginProvider {
    public ProjectSourcePlugin.PluginInstance provide(SourceType sourceType, SuppliedProperties properties) {
        GerritProjectSourcePlugin gerritProjectSourcePlugin = new GerritProjectSourcePlugin();

        if (!sourceType.equals(gerritProjectSourcePlugin.getSourceType())) {
            log.error("Source of type {} not supported", sourceType);
            throw new UnsupportedOperationException();
        }

        log.info("Instantiating source plugin {}", sourceType);

        gerritProjectSourcePlugin.validateProperties(properties);

        return gerritProjectSourcePlugin.instantiate(properties);
    }
}
