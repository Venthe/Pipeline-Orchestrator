package eu.venthe.pipeline.orchestrator.plugins;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface ConfigurationProvider {
    <T> Optional<T> getExecutorConfiguration(String pluginType, ConfigurationMapper<T> mapper);

    <T> Optional<T> getConfigurationForId(ConfigurationType configurationType, String pluginType, String pluginId, ConfigurationMapper<T> mapper);

    <T> Map<String, T> getConfigurationForType(ConfigurationType configurationType, String pluginType, ConfigurationMapper<T> mapper);

    interface ConfigurationMapper<T> extends Function<Map<String, ?>, T> {
    }

    enum ConfigurationType {
        PROJECT_PROVIDER,
        VERSION_CONTROL_SYSTEM
    }
}
