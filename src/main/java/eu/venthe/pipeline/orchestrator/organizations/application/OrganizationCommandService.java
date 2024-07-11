package eu.venthe.pipeline.orchestrator.organizations.application;

import eu.venthe.pipeline.orchestrator.organizations.application.dto.CreateOrganizationSpecification;
import eu.venthe.pipeline.orchestrator.organizations.application.dto.SourceConfigurationSpecification;
import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;

public interface OrganizationCommandService {
    OrganizationId create(CreateOrganizationSpecification specification);

    SourceConfigurationId addSourceConfiguration(SourceConfigurationSpecification specification);
}
