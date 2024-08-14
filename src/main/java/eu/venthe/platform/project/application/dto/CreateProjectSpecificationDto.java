package eu.venthe.platform.project.application.dto;

import eu.venthe.platform.shared_kernel.project.ProjectId;
import eu.venthe.platform.shared_kernel.project.ProjectStatus;

public record CreateProjectSpecificationDto(ProjectId projectId, ProjectStatus status) {
}
