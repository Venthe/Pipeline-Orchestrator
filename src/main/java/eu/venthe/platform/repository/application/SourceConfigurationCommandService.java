package eu.venthe.platform.repository.application;

import eu.venthe.platform.repository.domain.SourceConfiguration;
import eu.venthe.platform.repository.domain.SourceConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SourceConfigurationCommandService {
    private final SourceConfigurationRepository sourceConfigurationRepository;

    public void register(String name) {
        log.trace("Registering source configuration {}", name);
        if (sourceConfigurationRepository.exists(name)) {
            log.error("Source configuration {} already exists", name);
            throw new SourceConfigurationAlreadyExistsException();
        }

        var sourceConfiguration = new SourceConfiguration(name);
        sourceConfigurationRepository.save(sourceConfiguration);
        log.debug("Source configuration {} registered", name);
    }
}
