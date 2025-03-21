package eu.venthe.platform.projects.application;

import eu.venthe.platform.projects.domain.SourceConfigurationFactory;
import eu.venthe.platform.projects.domain.SourceConfigurationInternalIdentifier;
import eu.venthe.platform.projects.domain.SourceConfigurationMissingException;
import eu.venthe.platform.projects.domain.SourceConfigurationRepository;
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
    private final SourceConfigurationFactory sourceConfigurationFactory;

    public SourceConfigurationInternalIdentifier register(String sourceType) {
        return register(sourceType, Collections.emptyMap());
    }

    public SourceConfigurationInternalIdentifier register(String sourceType, Map<String, DynamicValue> properties) {

        var result = sourceConfigurationFactory.create(sourceType, properties);

        log.trace("Registering source configuration {}", result.data().getInternalIdentifier());
        if (sourceConfigurationRepository.exists(result.data().getInternalIdentifier())) {
            throw new SourceConfigurationAlreadyExistsException(result.data().getInternalIdentifier());
        }
        sourceConfigurationRepository.save(result.data());
        messageBroker.exchange(result.messages());
        log.debug("Source configuration {} registered", result.data().getInternalIdentifier());

        return result.data().getInternalIdentifier();
    }

    public void synchronizeAll(SourceConfigurationInternalIdentifier sourceIdentifier) {
        log.trace("Fully synchronizing source configuration {}", sourceIdentifier);
        var sourceConfiguration = sourceConfigurationRepository.find(sourceIdentifier)
                .orElseThrow(() -> new SourceConfigurationMissingException(sourceIdentifier));
        var result = sourceConfiguration.synchronizeAll();
        sourceConfigurationRepository.save(sourceConfiguration);
        messageBroker.exchange(result);
        log.debug("Source configuration {} synchronized", sourceIdentifier);
    }
}
