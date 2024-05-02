package eu.venthe.pipeline.orchestrator.projects_source.adapter;

import java.util.Collection;

public interface ProjectsProvider {
    Collection<ProjectDto> getProjects();
}
