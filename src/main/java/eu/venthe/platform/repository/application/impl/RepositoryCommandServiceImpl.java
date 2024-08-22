package eu.venthe.platform.repository.application.impl;

import eu.venthe.platform.repository.application.RepositoryCommandService;
import eu.venthe.platform.repository.application.model.CreateRepositorySpecification;
import eu.venthe.platform.repository.domain.Repository;
import eu.venthe.platform.repository.domain.infrastructure.RepositoryRepository;
import eu.venthe.platform.shared_kernel.events.Envelope;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.source_configuration.application.SourceQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RepositoryCommandServiceImpl implements RepositoryCommandService {
    private final RepositoryRepository repository;
    private final SourceQueryService sourceQueryService;
    private final MessageBroker messageBroker;

    @Override
    public void add(CreateRepositorySpecification createRepositorySpecification) {
        if (repository.exists(createRepositorySpecification.repositoryId())) {
            throw new IllegalArgumentException();
        }

        log.trace("Saving repository {}", createRepositorySpecification);
        var result = Repository.create(createRepositorySpecification.repositoryId(), createRepositorySpecification.configurationSourceId(), sourceQueryService);

        repository.save(result.getKey());
        messageBroker.exchange(Envelope.from(result.getValue()));
        log.debug("Repository saved {}", result.getKey());
    }
}
