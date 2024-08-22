package eu.venthe.platform.application.config;

import eu.venthe.platform.application.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.platform.repository.domain.Repository;
import eu.venthe.platform.repository.domain.RepositoryId;
import eu.venthe.platform.repository.domain.infrastructure.RepositoryRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryConfigurationRepositoryImpl extends InMemoryDomainRepository<Repository, RepositoryId>
        implements RepositoryRepository {
}
