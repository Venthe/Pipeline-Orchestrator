package eu.venthe.platform.registries.domain.plugins;

public interface RegistryPluginInstance {
    RegistryType getType();

    boolean checkHealth();
}
