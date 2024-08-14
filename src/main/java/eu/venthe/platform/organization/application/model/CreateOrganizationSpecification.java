package eu.venthe.platform.organization.application.model;

import eu.venthe.platform.organization.domain.OrganizationId;
import eu.venthe.platform.organization.domain.Sources;
import eu.venthe.platform.source_configuration.application.SourceConfigurationSpecification;
import lombok.Builder;

import java.util.Map;

@Builder
public record CreateOrganizationSpecification(
        OrganizationId organizationId,
        Map<Sources.SourceAlias, SourceConfigurationSpecification> sources
) {
    public static class CreateOrganizationSpecificationBuilder {
        public CreateOrganizationSpecificationBuilder organizationId(String organizationId) {
            this.organizationId = new OrganizationId(organizationId);
            return this;
        }
    }

}
