package eu.venthe.platform.organization.domain;

import eu.venthe.platform.organization.application.dto.CreateOrganizationSpecification;
import org.springframework.stereotype.Component;

@Component
public class OrganizationFactory {
    public Organization create(CreateOrganizationSpecification specification) {
        return new Organization(specification.organizationId());
    }
}
