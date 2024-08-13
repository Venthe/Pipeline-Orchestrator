package eu.venthe.platform.organization.application.impl;


import eu.venthe.platform.organization.application.OrganizationCommandService;
import eu.venthe.platform.organization.application.model.CreateOrganizationSpecification;
import eu.venthe.platform.organization.domain.Organization;
import eu.venthe.platform.organization.domain.OrganizationFactory;
import eu.venthe.platform.organization.domain.OrganizationId;
import eu.venthe.platform.organization.domain.infrastructure.OrganizationRepository;
import eu.venthe.platform.organization.domain.source_configuration.application.DataSourceQueryService;
import eu.venthe.platform.project.application.ProjectsCommandService;
import eu.venthe.platform.project.application.ProjectsQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationCommandService {
    private final FeatureManager featureManager;
    private final OrganizationRepository organizationRepository;
    private final OrganizationFactory organizationFactory;

    @Override
    public OrganizationId register(final CreateOrganizationSpecification specification) {
        if (!featureManager.isActive(new NamedFeature("ORGANIZATION_CREATE"))) {
            throw new UnsupportedOperationException("Organization creation is not enabled.");
        }

        if (organizationRepository.exists(specification.organizationId())) {
            throw new UnsupportedOperationException("Organization of id \"%s\" already exists".formatted(specification.organizationId().value()));
        }

        log.trace("Creating organization. {}", specification);
        Organization organization = organizationFactory.create(specification);
        organizationRepository.save(organization);
        log.info("Organization \"{}\" created.", specification.organizationId().value());
        return specification.organizationId();
    }

    @Override
    public void archive(final OrganizationId organizationId) {
        throw new UnsupportedOperationException();
    }
}
