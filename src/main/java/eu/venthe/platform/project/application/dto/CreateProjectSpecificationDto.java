package eu.venthe.platform.project.application.dto;

import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.project.domain.ProjectStatus;

public record CreateProjectSpecificationDto(ProjectId projectId, ProjectStatus status) {
}
