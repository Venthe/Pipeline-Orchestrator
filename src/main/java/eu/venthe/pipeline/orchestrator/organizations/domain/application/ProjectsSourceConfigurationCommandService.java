package eu.venthe.pipeline.orchestrator.organizations.domain.application;

import eu.venthe.pipeline.orchestrator.organizations.domain.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

public interface ProjectsSourceConfigurationCommandService {

    ProjectsSourceConfigurationId register(ProjectsSourceConfigurationId configurationId, SourceType sourceType, SuppliedProperties properties);

    void synchronize(ProjectsSourceConfigurationId configurationId);

    void unregister(ProjectsSourceConfigurationId configurationId);

    void synchronizeAll();
}
