package eu.venthe.platform.namespace.application.model;

import eu.venthe.platform.namespace.domain.NamespaceName;
import eu.venthe.platform.source_configuration.application.SourceConfigurationSpecification;
import lombok.Builder;

@Builder
public record CreateNamespaceSpecification(
        NamespaceName namespaceName,
        SourceConfigurationSpecification source
) {
    public static class CreateNamespaceSpecificationBuilder {
        public CreateNamespaceSpecificationBuilder namespaceName(String namespaceName) {
            this.namespaceName = new NamespaceName(namespaceName);
            return this;
        }
    }

}
