package eu.venthe.pipeline.orchestrator._archive.projects_and_orgs;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectStatus;

public record UpdateProjectSpecificationDto(ProjectStatus status, String projectName) {
}
