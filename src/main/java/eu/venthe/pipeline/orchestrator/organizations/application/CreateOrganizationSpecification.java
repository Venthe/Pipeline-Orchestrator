package eu.venthe.pipeline.orchestrator.organizations.application;

import eu.venthe.pipeline.orchestrator.organizations.domain.domain.OrganizationId;
import lombok.Builder;

@Builder
public record CreateOrganizationSpecification(OrganizationId organizationId) {
}
