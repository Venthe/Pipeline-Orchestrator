package eu.venthe.platform.project.application;

import eu.venthe.platform._archive.projects_and_orgs.ReadProjectSourceConfigurationDto;
import eu.venthe.platform.project.domain.source_configurations.SourceConfigurationId;
import eu.venthe.platform.project.domain.source_configurations.plugins.template.model.SourceType;

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
