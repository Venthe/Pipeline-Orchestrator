package eu.venthe.pipeline.orchestrator.organizations.application.dto;

import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import lombok.Builder;

@Builder
public record CreateOrganizationSpecification(OrganizationId organizationId) {
}
