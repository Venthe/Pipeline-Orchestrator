package eu.venthe.pipeline.orchestrator.organizations.domain.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.Organization;

public interface OrganizationRepository {
    void save(Organization organization);

    /**
     * Organization exists and it is not archived.
     */
    boolean isAvailable(OrganizationId organizationId);

    boolean exists(OrganizationId organizationId);
}
