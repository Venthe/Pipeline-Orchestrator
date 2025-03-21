package eu.venthe.platform.projects.plugin;

import eu.venthe.platform.shared_kernel.dynamic_value.DynamicValue;
import eu.venthe.platform.projects.plugin.template.RepositorySourcePlugin;
import eu.venthe.platform.projects.plugin.template.RepositorySourcePluginInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PluginProvider {
    private final Map<String, RepositorySourcePlugin> pluginProviders;

    public PluginProvider(Collection<RepositorySourcePlugin> repositorySourcePlugins) {
        this.pluginProviders = repositorySourcePlugins.stream()
                .collect(Collectors.toMap(
                        repositorySourcePlugin -> repositorySourcePlugin.getSourceType().toLowerCase(Locale.ROOT),
                        UnaryOperator.identity()
                ));
    }

    public RepositorySourcePluginInstance provide(String sourceType, Map<String, DynamicValue> properties) {
        var sourcePlugin = Optional.ofNullable(pluginProviders.get(sourceType.toLowerCase(Locale.ROOT))).orElseThrow(() -> new RuntimeException("No provider of type %s found".formatted(sourceType)));

        if (!sourceType.equals(sourcePlugin.getSourceType())) {
            log.error("Source of type {} not supported", sourceType);
            throw new UnsupportedOperationException();
        }

        // TODO: Add validations for supplied properties

        log.trace("Instantiating source plugin {}", sourceType);

        var instantiate = sourcePlugin.instantiate(Optional.ofNullable(properties).orElse(Collections.emptyMap()));

        log.info("Plugin for source type {} instantiated.", instantiate.getSourceType());
        return instantiate;
    }
}
