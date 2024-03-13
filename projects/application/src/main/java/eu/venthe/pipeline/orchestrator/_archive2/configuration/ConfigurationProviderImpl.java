/*
package eu.venthe.pipeline.orchestrator._archive2.configuration;

import eu.venthe.pipeline.orchestrator.plugins.projects.ConfigurationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.*;

@RequiredArgsConstructor
@Service
public class ConfigurationProviderImpl implements ConfigurationProvider {
    private final PluginConfiguration pluginConfiguration;

    @Override
    public <T> Optional<T> getExecutorConfiguration(String pluginType, ConfigurationMapper<T> mapper) {
        return ofNullable(pluginConfiguration.getJobExecutor())
                .filter(e -> e.getType().equalsIgnoreCase(pluginType))
                .map(PluginConfiguration.ConfigurationContent::getProperties)
                .map(mapper);
    }

    @Override
    public <T> Optional<T> getConfigurationForId(ConfigurationType configurationType, String pluginType, String pluginId, ConfigurationMapper<T> mapper) {
        return ofNullable(getRootByType(configurationType))
                .map(node -> node.get(pluginId))
                .filter(e -> e.getType().equalsIgnoreCase(pluginType))
                .map(PluginConfiguration.ConfigurationContent::getProperties)
                .map(mapper);
    }

    @Override
    public <T> Map<String, T> getConfigurationForType(ConfigurationType configurationType, String pluginType, ConfigurationMapper<T> mapper) {
        return getRootByType(configurationType).entrySet().stream()
                .filter(e -> ofNullable(e.getValue())
                        .map(PluginConfiguration.ConfigurationContent::getType)
                        .map(pluginType::equalsIgnoreCase)
                        .orElseThrow()
                )
                .map(e -> Map.entry(e.getKey(), mapper.apply(e.getValue().getProperties())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, PluginConfiguration.ConfigurationContent> getRootByType(ConfigurationType configurationType) {
        return switch (configurationType) {
            case PROJECT_PROVIDER -> pluginConfiguration.getProjectProvider();
            case VERSION_CONTROL_SYSTEM -> pluginConfiguration.getVersionControlSystem();
            default -> throw new UnsupportedOperationException();
        };
    }
}
*/
