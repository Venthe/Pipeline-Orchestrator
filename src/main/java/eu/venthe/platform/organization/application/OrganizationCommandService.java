package eu.venthe.platform.organization.application;

import eu.venthe.platform.organization.application.dto.CreateOrganizationSpecification;
import eu.venthe.platform.organization.domain.OrganizationId;

public interface OrganizationCommandService {
    OrganizationId create(CreateOrganizationSpecification specification);
}
