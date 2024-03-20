package eu.venthe.pipeline.orchestrator.projects.api;

import java.util.Collection;

public interface ProjectsQueryService {
    Collection<ProjectDto> listProjects();
}
