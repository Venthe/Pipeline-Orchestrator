package eu.venthe.pipeline.organization.domain.infrastructure;


import eu.venthe.pipeline.organization.domain.Organization;
import eu.venthe.pipeline.organization.domain.OrganizationId;

public interface OrganizationRepository {
    void save(Organization organization);

    /**
     * Organization exists and it is not archived.
     */
    boolean isAvailable(OrganizationId organizationId);

    boolean exists(OrganizationId organizationId);
}
