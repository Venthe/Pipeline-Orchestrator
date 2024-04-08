package eu.venthe.pipeline.orchestrator.projects.api;

import java.util.Collection;
import java.util.Optional;

public interface ProjectsQueryService {
    Collection<ProjectDto> listProjects();

    Optional<ProjectDto> find(String systemId, String projectName);
}
