package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;

public class OrganizationCreatedEvent implements DomainTrigger {
    @Override
    public String getType() {
        return "organization_created";
    }
}
