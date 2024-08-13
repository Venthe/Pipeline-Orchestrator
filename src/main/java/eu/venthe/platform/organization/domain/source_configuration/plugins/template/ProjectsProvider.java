package eu.venthe.platform.organization.domain.source_configuration.plugins.template;

import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.ProjectDto;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.ProjectId;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectsProvider {
    Stream<ProjectDto> getProjects();

    Stream<ProjectId> getProjectIds();

    Optional<ProjectDto> getProject(ProjectId id);
}
