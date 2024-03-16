package eu.venthe.pipeline.orchestrator.projects_source.api;

import java.util.Optional;
import java.util.Set;

public interface ProjectsSourceConfigurationQueryService {

    Set<ReadProjectSourceConfigurationDto> listConfigurations();

    Optional<ReadProjectSourceConfigurationDto> getConfiguration(String projectSourceConfigurationId);
}
