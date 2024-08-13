package eu.venthe.pipeline.projects.application;

import eu.venthe.pipeline._archive.projects_and_orgs.ReadProjectSourceConfigurationDto;
import eu.venthe.pipeline.projects.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.projects.domain.source_configurations.plugins.template.model.SourceType;

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
