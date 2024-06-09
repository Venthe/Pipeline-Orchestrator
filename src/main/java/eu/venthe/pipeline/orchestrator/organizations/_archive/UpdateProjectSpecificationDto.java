package eu.venthe.pipeline.orchestrator.organizations._archive;

import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectStatus;

public record UpdateProjectSpecificationDto(ProjectStatus status, String projectName) {
}
