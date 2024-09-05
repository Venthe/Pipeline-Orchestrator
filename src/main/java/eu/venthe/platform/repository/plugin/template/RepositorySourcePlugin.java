package eu.venthe.platform.repository.plugin.template;

import eu.venthe.platform.shared_kernel.dynamic_value.DynamicValue;

import java.util.Map;

// TODO: Add an option to provide required properties for the plugin instantiation
public interface RepositorySourcePlugin {
    String getSourceType();

    RepositorySourcePluginInstance instantiate(Map<String, DynamicValue> properties);
}
