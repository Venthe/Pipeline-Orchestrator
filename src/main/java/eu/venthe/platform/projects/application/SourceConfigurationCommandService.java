package eu.venthe.platform.projects.application;

import eu.venthe.platform.projects.domain.SourceConfiguration;
import eu.venthe.platform.projects.domain.SourceConfigurationRepository;
import eu.venthe.platform.projects.plugin.PluginProvider;
import eu.venthe.platform.shared_kernel.dynamic_value.DynamicValue;
import eu.venthe.platform.shared_kernel.events.DomainMessagesBroker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SourceConfigurationCommandService {
    private final SourceConfigurationRepository sourceConfigurationRepository;
    private final DomainMessagesBroker messageBroker;
    private final PluginProvider pluginProvider;

    public String register(String name, String sourceType) {
        return register(name, sourceType, Collections.emptyMap());
    }

    public String register(String name, String sourceType, Map<String, DynamicValue> properties) {
        log.trace("Registering source configuration {}", name);
        if (sourceConfigurationRepository.exists(name)) {
            throw new SourceConfigurationAlreadyExistsException(name);
        }

        var plugin = pluginProvider.provide(sourceType, properties);
        var result = SourceConfiguration.create(name, plugin);
        sourceConfigurationRepository.save(result.data());
        messageBroker.exchange(result.messages());
        log.debug("Source configuration {} registered", name);

        return result.data().getName();
    }

    public void synchronizeAll(String name) {
        log.trace("Fully synchronizing source configuration {}", name);
        var sourceConfiguration = sourceConfigurationRepository.find(name).orElseThrow();
        var result = sourceConfiguration.synchronizeAll();
        sourceConfigurationRepository.save(sourceConfiguration);
        messageBroker.exchange(result);
        log.debug("Source configuration {} synchronized", name);
    }
}
