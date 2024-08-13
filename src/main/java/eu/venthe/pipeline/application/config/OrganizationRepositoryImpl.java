package eu.venthe.pipeline.application.config;

import com.google.common.collect.MoreCollectors;
import eu.venthe.pipeline.application.infrastructure.in_memory_repository.InMemoryRepository;
import eu.venthe.pipeline.organization.domain.Organization;
import eu.venthe.pipeline.organization.domain.OrganizationId;
import eu.venthe.pipeline.organization.domain.infrastructure.OrganizationRepository;
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
