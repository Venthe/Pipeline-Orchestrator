package eu.venthe.pipeline.orchestrator.organizations.application;

import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import org.springframework.stereotype.Service;

@Service
public class OrganizationManager {
    public OrganizationId create(CreateOrganizationSpecification createOrganizationSpecification) {
        throw new UnsupportedOperationException();
    }

    public boolean addSourceConfiguration(OrganizationId organizationId, ProjectsSourceConfigurationId configurationId, SourceType sourceType, SuppliedProperties properties) {
        throw new UnsupportedOperationException();
    }
}
