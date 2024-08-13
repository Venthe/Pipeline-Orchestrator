package eu.venthe.platform.source_configuration.application;

import eu.venthe.platform.source_configuration.domain.ProjectsSourceConfiguration;
import eu.venthe.platform.source_configuration.domain.SourceConfigurationId;

public interface ProjectsSourceConfigurationCommandService {

    SourceConfigurationId register(ProjectsSourceConfiguration.Specification specification);

    void unregister(SourceConfigurationId configurationId);
}
