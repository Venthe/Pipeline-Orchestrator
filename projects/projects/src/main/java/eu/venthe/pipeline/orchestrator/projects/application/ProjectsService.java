package eu.venthe.pipeline.orchestrator.projects.application;

import java.util.Collection;

public interface ProjectsService {
    Collection<ProjectDto> listProjects();

    void addProject(CreateProjectSpecification newProjectDto);
}
