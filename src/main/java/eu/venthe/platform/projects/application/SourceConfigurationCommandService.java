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

    public SourceConfigurationInternalIdentifier register(SourceConfigurationInternalIdentifier sourceIdentifier, String sourceType) {
        return register(sourceIdentifier, sourceType, Collections.emptyMap());
    }

    public SourceConfigurationInternalIdentifier register(SourceConfigurationInternalIdentifier sourceIdentifier, String sourceType, Map<String, DynamicValue> properties) {
        log.trace("Registering source configuration {}", sourceIdentifier);
        if (sourceConfigurationRepository.exists(sourceIdentifier)) {
            throw new SourceConfigurationAlreadyExistsException(sourceIdentifier);
        }

        var result = sourceConfigurationFactory.create(sourceIdentifier, sourceType, properties);
        sourceConfigurationRepository.save(result.data());
        messageBroker.exchange(result.messages());
        log.debug("Source configuration {} registered", sourceIdentifier);

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
