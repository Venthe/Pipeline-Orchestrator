package eu.venthe.platform.registries.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class RegistryFactory {

    public Registry create() {
        log.trace("Creating new registry");
        var registryIdentifier = new RegistryIdentifier(UUID.randomUUID().toString());
        var registry = new Registry(registryIdentifier);
        log.debug("Registry created: {}", registry.getIdentifier());
        return registry;
    }
}
