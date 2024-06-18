package eu.venthe.pipeline.orchestrator.organizations.domain;

import eu.venthe.pipeline.orchestrator.organizations.application.dto.CreateOrganizationSpecification;
import org.springframework.stereotype.Component;

@Component
public class OrganizationFactory {
    public Organization create(CreateOrganizationSpecification specification) {
        return new Organization(specification.organizationId());
    }
}
