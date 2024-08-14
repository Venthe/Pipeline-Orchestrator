package eu.venthe.platform.namespace.domain.infrastructure;


import eu.venthe.platform.namespace.domain.Namespace;
import eu.venthe.platform.namespace.domain.NamespaceName;
import eu.venthe.platform.shared_kernel.DomainRepository;

public interface NamespaceRepository extends DomainRepository<Namespace, NamespaceName> {
}
