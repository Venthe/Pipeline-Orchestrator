package eu.venthe.platform.source_configuration.domain.plugins.template;

import eu.venthe.platform.source_configuration.domain.plugins.template.model.ProjectDto;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectsProvider {
    Stream<ProjectDto> getProjects();

    Stream<ProjectDto.Id> getProjectIds();

    Optional<ProjectDto> getProject(String id);
}
