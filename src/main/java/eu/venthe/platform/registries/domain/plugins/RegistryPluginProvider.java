package eu.venthe.platform.registries.domain.plugins;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
public class RegistryPluginProvider {
    private final Map<RegistryType, RegistryPlugin> plugins;

    public RegistryPluginProvider(Collection<RegistryPlugin> plugins) {
        this.plugins = plugins.stream()
                .collect(Collectors.toMap(
                        RegistryPlugin::getType,
                        UnaryOperator.identity()
                ));
    }

    public RegistryPluginInstance provide(RegistryPluginConfiguration configuration) {
        var plugin = ofNullable(plugins.get(configuration.registryType()))
                .orElseThrow(() -> new PluginNotFoundException(configuration.registryType()));

        log.trace("Instantiating registry plugin of type {}.", configuration.registryType());

        var instance = plugin.instantiate(configuration);
        log.info("Plugin for registry type {} instantiated.", configuration.registryType());

        return instance;
    }
}
