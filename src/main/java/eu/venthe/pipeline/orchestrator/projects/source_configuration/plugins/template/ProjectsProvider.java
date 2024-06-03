package eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template;

import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectsProvider {
    Stream<ProjectDto> getProjects();

    Stream<ProjectDto.Id> getProjectIds();

    Optional<ProjectDto> getProject(String id);
}
