package eu.venthe.pipeline.projects.application.dto;

import eu.venthe.pipeline.projects.domain.ProjectId;
import eu.venthe.pipeline.projects.domain.ProjectStatus;

public record CreateProjectSpecificationDto(ProjectId projectId, ProjectStatus status) {
}
