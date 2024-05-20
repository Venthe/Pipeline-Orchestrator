package eu.venthe.pipeline.orchestrator.projects_source.plugin.template;

import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.ProjectDto;

import java.util.Collection;

public interface ProjectsProvider {
    Collection<ProjectDto> getProjects();
}
