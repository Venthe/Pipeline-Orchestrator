package eu.venthe.pipeline.orchestrator.projects.application.dto;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectStatus;

public record CreateProjectSpecificationDto(ProjectId projectId, ProjectStatus status) {
}
