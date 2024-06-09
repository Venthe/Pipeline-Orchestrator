package eu.venthe.pipeline.orchestrator.organizations.application;

import eu.venthe.pipeline.orchestrator.organizations._archive.ReadProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.ProjectsSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.template.model.SourceType;

import java.util.Optional;
import java.util.Set;

public interface ProjectsSourceConfigurationQueryService {

    default Set<ReadProjectSourceConfigurationDto> listConfigurations() {
        throw new UnsupportedOperationException();
    }

    default Optional<ReadProjectSourceConfigurationDto> getConfiguration(ProjectsSourceConfigurationId configurationId) {
        throw new UnsupportedOperationException();
    }

    default Set<SourceType> listHandledSourceTypes() {
        throw new UnsupportedOperationException();
    }
}
