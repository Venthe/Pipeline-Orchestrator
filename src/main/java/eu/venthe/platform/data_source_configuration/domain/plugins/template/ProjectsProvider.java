package eu.venthe.platform.data_source_configuration.domain.plugins.template;

import eu.venthe.platform.data_source_configuration.domain.plugins.template.model.ProjectDto;
import eu.venthe.platform.data_source_configuration.plugins.template.model.ProjectDto;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectsProvider {
    Stream<ProjectDto> getProjects();

    Stream<ProjectDto.Id> getProjectIds();

    Optional<ProjectDto> getProject(String id);
}
