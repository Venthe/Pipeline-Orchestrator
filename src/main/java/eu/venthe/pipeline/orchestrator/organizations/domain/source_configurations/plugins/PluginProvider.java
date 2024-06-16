package eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins;

import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PluginProvider {
    private final FeatureManager featureManager;
    private final PluginProviders pluginProviders;

    public ProjectSourcePlugin.PluginInstance provide(SourceType sourceType, SuppliedProperties properties) {
        var sourcePlugin = pluginProviders.provide(sourceType).orElseThrow();

        if (!sourceType.equals(sourcePlugin.getSourceType())) {
            log.error("Source of type {} not supported", sourceType);
            throw new UnsupportedOperationException();
        }

        if (featureManager.isActive(new NamedFeature("VALIDATE_PROJECT_PLUGIN_PROPERTIES"))) {
            sourcePlugin.validateProperties(properties);
        }

        log.trace("Instantiating source plugin {}", sourceType);

        var instantiate = sourcePlugin.instantiate(properties);

        log.info("Plugin for source type {} instantiated.", instantiate.getSourceType());
        return instantiate;
    }

    @Component
    @ToString
    @EqualsAndHashCode
    public static class PluginProviders {
        private final Map<SourceType, ProjectSourcePlugin> providers;

        public PluginProviders(final Set<ProjectSourcePlugin> providers) {
            this.providers = providers.stream()
                    .collect(Collectors.toMap(
                            ProjectSourcePlugin::getSourceType,
                            Function.identity()
                    ));
        }

        public Optional<ProjectSourcePlugin> provide(SourceType sourceType) {
            return Optional.ofNullable(providers.get(sourceType));
        }
    }
}
