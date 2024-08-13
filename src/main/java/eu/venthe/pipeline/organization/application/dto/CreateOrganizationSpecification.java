package eu.venthe.pipeline.organization.application.dto;

import eu.venthe.pipeline.organization.domain.OrganizationId;
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
