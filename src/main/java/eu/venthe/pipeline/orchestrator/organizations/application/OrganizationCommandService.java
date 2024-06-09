package eu.venthe.pipeline.orchestrator.organizations.application;

import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

public interface OrganizationCommandService {
    OrganizationId create(CreateOrganizationSpecification specification);

    boolean addSourceConfiguration(OrganizationId organizationId,
                                   ProjectsSourceConfigurationId configurationId,
                                   SourceType sourceType,
                                   SuppliedProperties properties);
}
