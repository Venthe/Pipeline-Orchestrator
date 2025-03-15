package eu.venthe.platform.registries.domain;

import eu.venthe.platform.registries.domain.plugins.RegistryPluginConfiguration;
import eu.venthe.platform.registries.domain.plugins.RegistryPluginProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class RegistryFactory {
    private final RegistryPluginProvider pluginProvider;

    public Registry create(RegistryPluginConfiguration pluginConfiguration) {
        log.trace("Creating new registry");
        var registryIdentifier = new RegistryIdentifier(UUID.randomUUID().toString());
        var pluginInstance = pluginProvider.provide(pluginConfiguration);
        var registry = new Registry(registryIdentifier, pluginInstance);
        log.debug("Registry created: {}", registry.getIdentifier());
        return registry;
    }
}
