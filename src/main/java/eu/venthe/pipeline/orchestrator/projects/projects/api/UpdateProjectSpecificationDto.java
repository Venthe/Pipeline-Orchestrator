package eu.venthe.pipeline.orchestrator.projects.projects.api;

import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectStatus;

public record UpdateProjectSpecificationDto(ProjectStatus status, String projectName) {
}
