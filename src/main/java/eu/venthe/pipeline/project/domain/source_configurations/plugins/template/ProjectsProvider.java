package eu.venthe.pipeline.project.domain.source_configurations.plugins.template;

import eu.venthe.pipeline.project.domain.source_configurations.plugins.template.model.ProjectDto;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectsProvider {
    Stream<ProjectDto> getProjects();

    Stream<ProjectDto.Id> getProjectIds();

    Optional<ProjectDto> getProject(String id);
}
