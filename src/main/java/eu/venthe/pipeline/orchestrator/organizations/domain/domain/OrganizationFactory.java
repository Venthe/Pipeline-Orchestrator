package eu.venthe.pipeline.orchestrator.organizations.domain.domain;

import eu.venthe.pipeline.orchestrator.organizations.application.CreateOrganizationSpecification;
import org.springframework.stereotype.Component;

@Component
public class OrganizationFactory {
    public Organization create(CreateOrganizationSpecification specification) {
        throw new UnsupportedOperationException("Organization creation not yet supported.");
    }
}
