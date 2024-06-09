package eu.venthe.pipeline.orchestrator.organizations.domain.projects.api;

import eu.venthe.pipeline.orchestrator.organizations.domain.projects.domain.model.ProjectStatus;

public record UpdateProjectSpecificationDto(ProjectStatus status, String projectName) {
}
