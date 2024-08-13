package eu.venthe.platform.organization.application;

import eu.venthe.platform.organization.application.model.CreateOrganizationSpecification;
import eu.venthe.platform.organization.domain.OrganizationId;

public interface OrganizationCommandService {
    OrganizationId register(CreateOrganizationSpecification specification);

    void archive(OrganizationId organizationId);
}
