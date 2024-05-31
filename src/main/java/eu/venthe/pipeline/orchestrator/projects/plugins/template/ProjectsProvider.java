package eu.venthe.pipeline.orchestrator.projects.plugins.template;

import eu.venthe.pipeline.orchestrator.projects.plugins.template.model.ProjectDto;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectsProvider {
    Stream<ProjectDto> getProjects();

    Stream<ProjectDto.Id> getProjectIds();

    Optional<ProjectDto> getProject(String id);
}
