package eu.venthe.platform.registries.domain.plugins;

public interface RegistryPlugin {
    RegistryType getType();

    RegistryPluginInstance instantiate(RegistryPluginConfiguration configuration);
}
