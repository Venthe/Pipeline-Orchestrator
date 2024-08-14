package eu.venthe.platform.application.config;

import eu.venthe.platform.application.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.platform.namespace.domain.Namespace;
import eu.venthe.platform.namespace.domain.NamespaceName;
import eu.venthe.platform.namespace.domain.infrastructure.NamespaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NamespaceRepositoryImpl extends InMemoryDomainRepository<Namespace, NamespaceName> implements NamespaceRepository {
}
