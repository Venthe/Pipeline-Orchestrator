package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template.model.SourceType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

public interface ProjectsSourceConfigurationCommandService {

    SourceConfigurationId register(SourceConfigurationId configurationId, SourceType sourceType, SuppliedProperties properties);

    void synchronize(SourceConfigurationId configurationId);

    void unregister(SourceConfigurationId configurationId);

    void synchronizeAll();
}
