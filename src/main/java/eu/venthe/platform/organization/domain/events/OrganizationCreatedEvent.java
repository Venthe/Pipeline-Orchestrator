package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
@Getter
public class OrganizationCreatedEvent implements DomainTrigger {
    private final OrganizationName organizationName;

    @Override
    public String getType() {
        return "organization_created";
    }
}
