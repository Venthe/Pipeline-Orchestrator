package eu.venthe.platform.projects.application;

import eu.venthe.platform.projects.domain.SourceConfiguration;
import eu.venthe.platform.projects.domain.SourceConfigurationRepository;
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
            throw new SourceConfigurationAlreadyExistsException(name);
        }

        var sourceConfiguration = new SourceConfiguration(name);
        sourceConfigurationRepository.save(sourceConfiguration);
        log.debug("Source configuration {} registered", name);
    }
}
