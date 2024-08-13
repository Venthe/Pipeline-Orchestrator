package eu.venthe.platform.organization.application.dto;

import eu.venthe.platform.organization.domain.OrganizationId;
import eu.venthe.platform.source_configuration.domain.SourceConfigurationId;
import lombok.Builder;

@Builder
public record CreateOrganizationSpecification(OrganizationId organizationId, SourceConfigurationId sourceConfigurationId) {
    public static class CreateOrganizationSpecificationBuilder {
        public CreateOrganizationSpecificationBuilder organizationId(String organizationId) {
            this.organizationId = new OrganizationId(organizationId);
            return this;
        }

        public CreateOrganizationSpecificationBuilder sourceConfigurationId(String sourceConfigurationId) {
            this.sourceConfigurationId = new SourceConfigurationId(sourceConfigurationId);
            return this;
        }
    }
}
