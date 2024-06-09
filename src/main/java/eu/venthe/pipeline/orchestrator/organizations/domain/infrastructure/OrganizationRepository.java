package eu.venthe.pipeline.orchestrator.organizations.domain.infrastructure;


import eu.venthe.pipeline.orchestrator.organizations.domain.Organization;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;

public interface OrganizationRepository {
    void save(Organization organization);

    /**
     * Organization exists and it is not archived.
     */
    boolean isAvailable(OrganizationId organizationId);

    boolean exists(OrganizationId organizationId);
}
