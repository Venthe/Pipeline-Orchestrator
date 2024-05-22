package eu.venthe.pipeline.orchestrator.projects.plugin.template;

import eu.venthe.pipeline.orchestrator.projects.plugin.template.model.ProjectDto;

import java.util.Collection;

public interface ProjectsProvider {
    Collection<ProjectDto> getProjects();
}
