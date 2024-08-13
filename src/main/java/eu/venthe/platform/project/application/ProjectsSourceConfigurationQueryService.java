package eu.venthe.platform.project.application;

import eu.venthe.platform._archive.projects_and_orgs.ReadProjectSourceConfigurationDto;
import eu.venthe.platform.data_source_configuration.SourceConfigurationId;
import eu.venthe.platform.data_source_configuration.plugins.template.model.SourceType;

import java.util.Optional;
import java.util.Set;

public interface ProjectsSourceConfigurationQueryService {

    default Set<ReadProjectSourceConfigurationDto> listConfigurations() {
        throw new UnsupportedOperationException();
    }

    default Optional<ReadProjectSourceConfigurationDto> getConfiguration(SourceConfigurationId configurationId) {
        throw new UnsupportedOperationException();
    }

    default Set<SourceType> listHandledSourceTypes() {
        throw new UnsupportedOperationException();
    }
}
