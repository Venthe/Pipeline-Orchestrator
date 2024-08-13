package eu.venthe.pipeline.organizations.domain;

import eu.venthe.pipeline.organizations.application.dto.CreateOrganizationSpecification;
import org.springframework.stereotype.Component;

@Component
public class OrganizationFactory {
    public Organization create(CreateOrganizationSpecification specification) {
        return new Organization(specification.organizationId());
    }
}
