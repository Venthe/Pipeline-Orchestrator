package eu.venthe.platform.repository.application;

import eu.venthe.platform.shared_kernel.dynamic_value.DynamicValue;
import eu.venthe.platform.shared_kernel.events.DomainMessagesBroker;
import eu.venthe.platform.repository.domain.SourceConfiguration;
import eu.venthe.platform.repository.domain.SourceConfigurationRepository;
import eu.venthe.platform.repository.plugin.PluginProvider;
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

    public void register(String name, String sourceType) {
        register(name, sourceType, Collections.emptyMap());
    }

    public void register(String name, String sourceType, Map<String, DynamicValue> properties) {
        log.trace("Registering source configuration {}", name);
        if (sourceConfigurationRepository.exists(name)) {
            log.error("Source configuration {} already exists", name);
            throw new SourceConfigurationAlreadyExistsException();
        }

        var plugin = pluginProvider.provide(sourceType, properties);
        var result = SourceConfiguration.create(name, plugin);
        sourceConfigurationRepository.save(result.data());
        messageBroker.exchange(result.messages());
        log.debug("Source configuration {} registered", name);
    }

    public void synchronize(String name) {
        log.trace("Synchronizing source configuration {}", name);
        var sourceConfiguration = sourceConfigurationRepository.find(name).orElseThrow();
        var result = sourceConfiguration.synchronize();
        sourceConfigurationRepository.save(sourceConfiguration);
        messageBroker.exchange(result);
        log.debug("Source configuration {} synchronized", name);
    }
}
