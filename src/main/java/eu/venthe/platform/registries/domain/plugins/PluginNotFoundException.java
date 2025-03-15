package eu.venthe.platform.registries.domain.plugins;

public class PluginNotFoundException extends RuntimeException {
    public PluginNotFoundException(RegistryType registryType) {
        super("Plugin not found: %s".formatted(registryType));
    }
}
