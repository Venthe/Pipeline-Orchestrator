package eu.venthe.platform.projects.domain;

import eu.venthe.platform.projects.domain.events.SourceRegisteredEvent;
import eu.venthe.platform.projects.plugin.PluginProvider;
import eu.venthe.platform.shared_kernel.DomainResult;
import eu.venthe.platform.shared_kernel.dynamic_value.DynamicValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SourceConfigurationFactory {
    private final PluginProvider pluginProvider;

    public DomainResult<SourceConfiguration> create(String sourceType,
                                                    Map<String, DynamicValue> properties) {
        var sourceIdentifier = new SourceConfigurationInternalIdentifier(UUID.randomUUID().toString());
        var plugin = pluginProvider.provide(sourceType, properties);
        var sourceConfiguration = new SourceConfiguration(sourceIdentifier, plugin);
        return DomainResult.from(sourceConfiguration, new SourceRegisteredEvent(sourceIdentifier));
    }
}
