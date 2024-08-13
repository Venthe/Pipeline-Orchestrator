package eu.venthe.platform.project.application;

import eu.venthe.platform.source_configuration.SourceConfigurationId;
import eu.venthe.platform.source_configuration.plugins.template.model.SourceType;
import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;

public interface ProjectsSourceConfigurationCommandService {

    SourceConfigurationId register(SourceConfigurationId configurationId, SourceType sourceType, SuppliedProperties properties);

    void synchronize(SourceConfigurationId configurationId);

    void unregister(SourceConfigurationId configurationId);

    void synchronizeAll();
}
