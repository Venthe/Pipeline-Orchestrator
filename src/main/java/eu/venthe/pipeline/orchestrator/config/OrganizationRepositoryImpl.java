package eu.venthe.pipeline.orchestrator.config;

import com.google.common.collect.MoreCollectors;
import eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repository.InMemoryRepository;
import eu.venthe.pipeline.orchestrator.organizations.domain.Organization;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.infrastructure.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {
    private final InMemoryRepository<Organization, OrganizationId> repository = new InMemoryRepository<>();

    @Override
    public void save(Organization organization) {
        repository.save(organization.getOrganizationId(), organization);
    }

    @Override
    public boolean isAvailable(OrganizationId organizationId) {
        return find(organizationId).orElseThrow().isActive();
    }

    @Override
    public boolean exists(OrganizationId organizationId) {
        return find(organizationId).isPresent();
    }

    private Optional<Organization> find(OrganizationId organizationId) {
        return repository.getAll().stream()
                .filter(o -> o.getOrganizationId().equals(organizationId))
                .collect(MoreCollectors.toOptional());
    }
}
