package eu.venthe.platform.organization.application.dto;

import eu.venthe.platform.organization.domain.OrganizationId;
import lombok.Builder;

@Builder
public record CreateOrganizationSpecification(OrganizationId organizationId) {
    public static class CreateOrganizationSpecificationBuilder {
        public CreateOrganizationSpecificationBuilder organizationId(String organizationId) {
            this.organizationId = new OrganizationId(organizationId);
            return this;
        }
    }
}
