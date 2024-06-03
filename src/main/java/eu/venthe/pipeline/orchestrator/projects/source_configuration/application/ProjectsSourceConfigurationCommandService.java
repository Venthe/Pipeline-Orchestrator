package eu.venthe.pipeline.orchestrator.projects.source_configuration.application;

import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.model.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

public interface ProjectsSourceConfigurationCommandService {

    ProjectsSourceConfigurationId register(ProjectsSourceConfigurationId configurationId, SourceType sourceType, SuppliedProperties properties);

    void synchronize(ProjectsSourceConfigurationId configurationId);

    void unregister(ProjectsSourceConfigurationId configurationId);

    void synchronizeAll();
}
