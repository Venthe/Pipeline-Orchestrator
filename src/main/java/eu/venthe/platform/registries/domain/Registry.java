package eu.venthe.platform.registries.domain;

import eu.venthe.platform.registries.domain.plugins.RegistryPluginInstance;
import eu.venthe.platform.registries.domain.plugins.RegistryType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor(access = AccessLevel.MODULE)
public class Registry {

    @Getter
    @EqualsAndHashCode.Include
    private final RegistryIdentifier identifier;
    private final RegistryPluginInstance registryPlugin;

    public boolean checkHealth() {
        log.trace("Checking registry {} health.", identifier.toString());

        return registryPlugin.checkHealth();
    }

    public RegistryType getType() {
        return registryPlugin.getType();
    }
}
