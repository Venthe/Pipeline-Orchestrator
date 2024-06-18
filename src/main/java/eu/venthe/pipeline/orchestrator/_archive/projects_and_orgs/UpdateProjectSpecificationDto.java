package eu.venthe.pipeline.orchestrator._archive.projects_and_orgs;

import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectStatus;

public record UpdateProjectSpecificationDto(ProjectStatus status, String projectName) {
}
