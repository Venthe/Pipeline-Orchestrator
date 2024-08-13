package eu.venthe.pipeline.organization.application;

import eu.venthe.pipeline.organization.application.dto.CreateOrganizationSpecification;
import eu.venthe.pipeline.organization.application.dto.SourceConfigurationSpecification;
import eu.venthe.pipeline.organization.domain.OrganizationId;
import eu.venthe.pipeline.project.domain.source_configurations.SourceConfigurationId;

public interface OrganizationCommandService {
    OrganizationId create(CreateOrganizationSpecification specification);

    SourceConfigurationId addSourceConfiguration(SourceConfigurationSpecification specification);
}
