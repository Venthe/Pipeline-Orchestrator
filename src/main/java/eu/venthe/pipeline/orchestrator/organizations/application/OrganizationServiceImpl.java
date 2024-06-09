package eu.venthe.pipeline.orchestrator.organizations.application;


import eu.venthe.pipeline.orchestrator.organizations.domain.Organization;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationFactory;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.infrastructure.OrganizationRepository;
import eu.venthe.pipeline.orchestrator.organizations.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
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
    private final SourceConfigurationRepository sourceConfigurationRepository;
    private final OrganizationRepository organizationRepository;
    private final OrganizationFactory organizationFactory;

    @Override
    public OrganizationId create(CreateOrganizationSpecification specification) {
        if (!featureManager.isActive(new NamedFeature("ORGANIZATION_CREATE"))) {
            throw new UnsupportedOperationException("Organization creation is not enabled.");
        }

        if (organizationRepository.exists(specification.organizationId())) {
            throw new UnsupportedOperationException("Organization of id \"%s\" already exists".formatted(specification.organizationId().value()));
        }

        log.info("Creating organization. {}", specification);
        Organization organization = organizationFactory.create(specification);
        organizationRepository.save(organization);
        log.info("Organization \"{}\" created.", specification.organizationId().value());
        return specification.organizationId();
    }

    @Override
    public boolean addSourceConfiguration(OrganizationId organizationId,
                                          ProjectsSourceConfigurationId configurationId,
                                          SourceType sourceType,
                                          SuppliedProperties properties) {
        log.info("Adding source configuration to organization {}.", organizationId.value());
        if (!organizationRepository.isAvailable(organizationId)) {
            throw new IllegalArgumentException("Organization of ID \"%s\" does not exist or is archived.".formatted(organizationId.value()));
        }

        throw new UnsupportedOperationException("Adding source configuration to organization is not yet supported.");
    }
}
