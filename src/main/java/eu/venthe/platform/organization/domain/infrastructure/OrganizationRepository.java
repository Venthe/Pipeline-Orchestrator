package eu.venthe.platform.organization.domain.infrastructure;


import eu.venthe.platform.organization.domain.Organization;
import eu.venthe.platform.organization.domain.OrganizationId;

public interface OrganizationRepository {
    void save(Organization organization);

    /**
     * Organization exists and it is not archived.
     */
    boolean isAvailable(OrganizationId organizationId);

    boolean exists(OrganizationId organizationId);
}
