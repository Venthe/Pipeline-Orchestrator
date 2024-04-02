package eu.venthe.pipeline.orchestrator.projects_source.api;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectPlugin;

import java.util.Optional;
import java.util.Set;

public interface ProjectsSourceConfigurationQueryService {

    Set<ReadProjectSourceConfigurationDto> listConfigurations();

    Optional<ReadProjectSourceConfigurationDto> getConfiguration(String projectSourceConfigurationId);

    Set<String> listSystemTypes();

    Optional<ProjectPlugin> getPluginDefinition(String systemType);
}
