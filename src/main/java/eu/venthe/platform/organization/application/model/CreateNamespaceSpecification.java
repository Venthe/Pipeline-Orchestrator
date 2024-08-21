package eu.venthe.platform.organization.application.model;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.source_configuration.application.SourceConfigurationSpecification;
import lombok.Builder;

@Builder
public record CreateOrganizationSpecification(
        OrganizationName organizationName,
        SourceConfigurationSpecification source
) {
    public static class CreateOrganizationSpecificationBuilder {
        public CreateOrganizationSpecificationBuilder organizationName(String organizationName) {
            this.organizationName = new OrganizationName(organizationName);
            return this;
        }
    }

}
