package eu.venthe.pipeline.organizations.application;

import eu.venthe.pipeline.organizations.application.dto.CreateOrganizationSpecification;
import eu.venthe.pipeline.organizations.application.dto.SourceConfigurationSpecification;
import eu.venthe.pipeline.organizations.domain.OrganizationId;
import eu.venthe.pipeline.projects.domain.source_configurations.SourceConfigurationId;

public interface OrganizationCommandService {
    OrganizationId create(CreateOrganizationSpecification specification);

    SourceConfigurationId addSourceConfiguration(SourceConfigurationSpecification specification);
}
