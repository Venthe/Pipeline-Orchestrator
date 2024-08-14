package eu.venthe.platform.application.config;

import com.google.common.collect.MoreCollectors;
import eu.venthe.platform.application.infrastructure.in_memory_repository.InMemoryRepository;
import eu.venthe.platform.namespace.domain.Namespace;
import eu.venthe.platform.namespace.domain.NamespaceName;
import eu.venthe.platform.namespace.domain.infrastructure.NamespaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NamespaceRepositoryImpl implements NamespaceRepository {
    private final InMemoryRepository<Namespace, NamespaceName> repository = new InMemoryRepository<>();

    @Override
    public void save(Namespace namespace) {
        repository.save(namespace.getNamespaceName(), namespace);
    }

    @Override
    public boolean exists(NamespaceName namespaceName) {
        return find(namespaceName).isPresent();
    }

    private Optional<Namespace> find(NamespaceName namespaceName) {
        return repository.getAll().stream()
                .filter(o -> o.getNamespaceName().equals(namespaceName))
                .collect(MoreCollectors.toOptional());
    }
}
