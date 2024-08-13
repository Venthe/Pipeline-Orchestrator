package eu.venthe.platform.organization.application.model;

import eu.venthe.platform.organization.domain.OrganizationId;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.SourceType;
import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.Builder;

@Builder
public record CreateOrganizationSpecification(
        OrganizationId organizationId,
        SourceConfigurationSpecification sourceConfigurationSpecification
) {
    public static class CreateOrganizationSpecificationBuilder {
        public CreateOrganizationSpecificationBuilder organizationId(String organizationId) {
            this.organizationId = new OrganizationId(organizationId);
            return this;
        }
    }

    @Builder
    public record SourceConfigurationSpecification(SourceType sourceType, SuppliedProperties properties) {
        public static class SourceConfigurationSpecificationBuilder {
            private SuppliedProperties properties = SuppliedProperties.none();

            public SourceConfigurationSpecification.SourceConfigurationSpecificationBuilder sourceType(String sourceType) {
                this.sourceType = new SourceType(sourceType);
                return this;
            }
        }
    }
}
