package eu.venthe.pipeline.orchestrator.projects.api;

import java.util.Collection;

public interface ProjectsService {
    Collection<AddProjectDto> listProjects();

    void addProject(CreateProjectSpecification newProjectDto);
}
