package eu.venthe.pipeline.orchestrator.organizations.domain;

import lombok.Value;

@Value
public class Organization {
    OrganizationId organizationId;

    public boolean isActive() {
        // TODO: Add status logic
        return true;
    }
}
