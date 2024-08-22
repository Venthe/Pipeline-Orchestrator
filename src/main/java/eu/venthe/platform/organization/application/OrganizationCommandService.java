package eu.venthe.platform.organization.application;

import eu.venthe.platform.organization.application.model.CreateOrganizationSpecification;
import eu.venthe.platform.organization.domain.OrganizationName;

public interface OrganizationCommandService {
    OrganizationName register(CreateOrganizationSpecification specification);

    void archive(OrganizationName organizationName);

    void synchronize(OrganizationName name);
}
