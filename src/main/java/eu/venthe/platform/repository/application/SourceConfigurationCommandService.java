package eu.venthe.platform.repository.application;

import eu.venthe.platform.repository.domain.SourceConfiguration;
import eu.venthe.platform.repository.domain.SourceConfigurationRepository;
import eu.venthe.platform.shared_kernel.events.DomainMessagesBroker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SourceConfigurationCommandService {
    private final SourceConfigurationRepository sourceConfigurationRepository;
    private final DomainMessagesBroker messageBroker;

    public void register(String name) {
        log.trace("Registering source configuration {}", name);
        if (sourceConfigurationRepository.exists(name)) {
            log.error("Source configuration {} already exists", name);
            throw new SourceConfigurationAlreadyExistsException();
        }

        var result = SourceConfiguration.create(name);
        sourceConfigurationRepository.save(result.data());
        messageBroker.exchange(result.messages());
        log.debug("Source configuration {} registered", name);
    }
}
