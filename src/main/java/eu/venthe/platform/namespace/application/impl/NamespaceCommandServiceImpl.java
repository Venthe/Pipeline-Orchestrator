package eu.venthe.platform.namespace.application.impl;

import eu.venthe.platform.namespace.application.NamespaceCommandService;
import eu.venthe.platform.namespace.application.model.CreateNamespaceSpecification;
import eu.venthe.platform.namespace.domain.NamespaceFactory;
import eu.venthe.platform.namespace.domain.NamespaceName;
import eu.venthe.platform.namespace.domain.infrastructure.NamespaceRepository;
import eu.venthe.platform.shared_kernel.events.Envelope;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NamespaceCommandServiceImpl implements NamespaceCommandService {
    private final NamespaceRepository namespaceRepository;
    private final NamespaceFactory namespaceFactory;
    private final MessageBroker messageBroker;

    @Override
    public NamespaceName register(final CreateNamespaceSpecification specification) {
        if (namespaceRepository.exists(specification.namespaceName())) {
            throw new UnsupportedOperationException("Namespace \"%s\" already exists".formatted(specification.namespaceName().value()));
        }

        log.trace("Creating namespace. {}", specification);
        var result = namespaceFactory.create(specification);
        namespaceRepository.save(result.getKey());
        log.info("Namespace \"{}\" created.", result.getKey().getNamespaceName());

        messageBroker.exchange(Envelope.from(result.getRight()));

        return specification.namespaceName();
    }

    @Override
    public void archive(final NamespaceName namespaceName) {
        throw new UnsupportedOperationException();
    }
}
