package eu.venthe.pipeline.orchestrator.organizations.application;


import eu.venthe.pipeline.orchestrator.organizations.domain.Organization;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationFactory;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.infrastructure.OrganizationRepository;
import eu.venthe.pipeline.orchestrator.organizations.domain.infrastructure.SourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.PluginProvider;
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
    private final SourceConfigurationRepository configurationRepository;
    private final OrganizationRepository organizationRepository;
    private final OrganizationFactory organizationFactory;
    private final PluginProvider pluginProvider;

    @Override
    public OrganizationId create(CreateOrganizationSpecification specification) {
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
    public SourceConfigurationId addSourceConfiguration(OrganizationId organizationId,
                                                        SourceConfigurationId configurationId,
                                                        SourceType sourceType,
                                                        SuppliedProperties properties) {
        log.trace("Adding source configuration to organization {}.", organizationId.value());
        if (!organizationRepository.isAvailable(organizationId)) {
            throw new IllegalArgumentException("Organization of ID \"%s\" does not exist or is archived.".formatted(organizationId.value()));
        }

        if (configurationRepository.exists(configurationId)) {
            throw new IllegalArgumentException("Configuration \"%s\" for organization \"%s\" already exists.".formatted(configurationId.id(), organizationId.value()));
        }

        var pluginInstance = pluginProvider.provide(sourceType, properties);

        var configuration = ProjectsSourceConfiguration.createNew(configurationId, pluginInstance);
        configurationRepository.save(configuration);

        log.info("Source configuration {} added to organization {}.", configuration.getConfigurationId(),  organizationId.value());
        return configuration.getConfigurationId();
    }
}
