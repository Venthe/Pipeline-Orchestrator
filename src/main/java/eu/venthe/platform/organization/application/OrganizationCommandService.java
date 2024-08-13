package eu.venthe.platform.organization.application;

import eu.venthe.platform.organization.application.dto.CreateOrganizationSpecification;
import eu.venthe.platform.organization.application.dto.SourceConfigurationSpecification;
import eu.venthe.platform.organization.domain.OrganizationId;
import eu.venthe.platform.source_configuration.SourceConfigurationId;

public interface OrganizationCommandService {
    OrganizationId create(CreateOrganizationSpecification specification);

    SourceConfigurationId addSourceConfiguration(SourceConfigurationSpecification specification);
}
