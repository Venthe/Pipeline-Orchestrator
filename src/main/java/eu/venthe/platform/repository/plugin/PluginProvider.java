package eu.venthe.platform.repository.plugin;

import eu.venthe.platform.shared_kernel.dynamic_value.DynamicValue;
import eu.venthe.platform.repository.plugin.template.RepositorySourcePlugin;
import eu.venthe.platform.repository.plugin.template.RepositorySourcePluginInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PluginProvider {
    private final Map<String, RepositorySourcePlugin> pluginProviders;

    public PluginProvider(Collection<RepositorySourcePlugin> repositorySourcePlugins) {
        this.pluginProviders = repositorySourcePlugins.stream()
                .collect(Collectors.toMap(
                        RepositorySourcePlugin::getSourceType,
                        UnaryOperator.identity()
                ));
    }

    public RepositorySourcePluginInstance provide(String sourceType, Map<String, DynamicValue> properties) {
        var sourcePlugin = Optional.ofNullable(pluginProviders.get(sourceType)).orElseThrow();

        if (!sourceType.equals(sourcePlugin.getSourceType())) {
            log.error("Source of type {} not supported", sourceType);
            throw new UnsupportedOperationException();
        }

        // TODO: Add validations for supplied properties

        log.trace("Instantiating source plugin {}", sourceType);

        var instantiate = sourcePlugin.instantiate(properties);

        log.info("Plugin for source type {} instantiated.", instantiate.getSourceType());
        return instantiate;
    }
}
