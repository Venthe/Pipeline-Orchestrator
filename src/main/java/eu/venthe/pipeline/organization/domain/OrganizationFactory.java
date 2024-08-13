package eu.venthe.pipeline.organization.domain;

import eu.venthe.pipeline.organization.application.dto.CreateOrganizationSpecification;
import org.springframework.stereotype.Component;

@Component
public class OrganizationFactory {
    public Organization create(CreateOrganizationSpecification specification) {
        return new Organization(specification.organizationId());
    }
}
