package eu.venthe.platform.namespace.domain.infrastructure;


import eu.venthe.platform.namespace.domain.Namespace;
import eu.venthe.platform.namespace.domain.NamespaceName;

public interface NamespaceRepository {
    void save(Namespace namespace);

    boolean exists(NamespaceName namespaceName);
}
