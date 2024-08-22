package eu.venthe.platform.organization.domain.infrastructure;

import eu.venthe.platform.organization.domain.Organization;
import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.shared_kernel.DomainRepository;

public interface OrganizationRepository extends DomainRepository<Organization, OrganizationName> {
}
