package eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins;

import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.gerrit.GerritProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

@Slf4j
@Component
@RequiredArgsConstructor
public class PluginProvider {
    private final FeatureManager featureManager;

    public ProjectSourcePlugin.PluginInstance provide(SourceType sourceType, SuppliedProperties properties) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        GerritProjectSourcePlugin gerritProjectSourcePlugin = new GerritProjectSourcePlugin();

        if (!sourceType.equals(gerritProjectSourcePlugin.getSourceType())) {
            log.error("Source of type {} not supported", sourceType);
            throw new UnsupportedOperationException();
        }

        if (featureManager.isActive(new NamedFeature("VALIDATE_PROJECT_PLUGIN_PROPERTIES"))) {
            gerritProjectSourcePlugin.validateProperties(properties);
        }

        log.info("Instantiating source plugin {}", sourceType);

        return gerritProjectSourcePlugin.instantiate(properties);
    }
}
