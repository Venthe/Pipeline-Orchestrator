package eu.venthe.platform.projects.domain;

import eu.venthe.platform.projects.domain.events.SourceRegisteredEvent;
import eu.venthe.platform.projects.plugin.PluginProvider;
import eu.venthe.platform.shared_kernel.DomainResult;
import eu.venthe.platform.shared_kernel.dynamic_value.DynamicValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class SourceConfigurationFactory {
    private final PluginProvider pluginProvider;

    public DomainResult<SourceConfiguration> create(SourceConfigurationInternalIdentifier identifier,
                                                    String sourceType,
                                                    Map<String, DynamicValue> properties) {
        var plugin = pluginProvider.provide(sourceType, properties);
        var sourceConfiguration = new SourceConfiguration(identifier, plugin);
        return DomainResult.from(sourceConfiguration, new SourceRegisteredEvent(identifier));
    }
}
