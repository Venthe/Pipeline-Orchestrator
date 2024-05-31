package eu.venthe.pipeline.orchestrator.projects.source_configuration.application;

import eu.venthe.pipeline.orchestrator.projects.source_configuration.api.ReadProjectSourceConfigurationDto;

import java.util.Optional;
import java.util.Set;

public interface ProjectsSourceConfigurationQueryService {

    Set<ReadProjectSourceConfigurationDto> listConfigurations();

    Optional<ReadProjectSourceConfigurationDto> getConfiguration(String projectSourceConfigurationId);

    Set<String> listSystemTypes();

//    Optional<ProjectSourceAdapter> getPluginDefinition(String systemType);
}
