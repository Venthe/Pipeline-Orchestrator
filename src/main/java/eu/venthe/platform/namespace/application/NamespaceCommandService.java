package eu.venthe.platform.namespace.application;

import eu.venthe.platform.namespace.application.model.CreateNamespaceSpecification;
import eu.venthe.platform.namespace.domain.NamespaceName;

public interface NamespaceCommandService {
    NamespaceName register(CreateNamespaceSpecification specification);

    void archive(NamespaceName namespaceName);

    void synchronize(NamespaceName name);
}
