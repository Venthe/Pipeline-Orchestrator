package eu.venthe.pipeline.orchestrator.projects_source.adapter.template;

import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.model.ProjectDto;

import java.util.Collection;

public interface ProjectsProvider {
    Collection<ProjectDto> getProjects();
}
