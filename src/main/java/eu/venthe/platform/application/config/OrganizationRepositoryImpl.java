package eu.venthe.platform.application.config;

import eu.venthe.platform.application.infrastructure.in_memory_repository.InMemoryDomainRepository;
import eu.venthe.platform.organization.domain.Organization;
import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.organization.domain.infrastructure.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationRepositoryImpl extends InMemoryDomainRepository<Organization, OrganizationName> implements OrganizationRepository {
}
