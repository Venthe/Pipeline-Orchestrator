package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.Organization;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.infrastructure.OrganizationRepository;
import org.springframework.stereotype.Component;

@Component
public class OrganizationRepositoryImpl implements OrganizationRepository {
    @Override
    public void save(Organization organization) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAvailable(OrganizationId organizationId) {
        throw new UnsupportedOperationException();
    }
}
