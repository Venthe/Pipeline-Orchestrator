package eu.venthe.pipeline.organizations.domain.infrastructure;


import eu.venthe.pipeline.organizations.domain.Organization;
import eu.venthe.pipeline.organizations.domain.OrganizationId;

public interface OrganizationRepository {
    void save(Organization organization);

    /**
     * Organization exists and it is not archived.
     */
    boolean isAvailable(OrganizationId organizationId);

    boolean exists(OrganizationId organizationId);
}
