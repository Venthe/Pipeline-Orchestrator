package eu.venthe.pipeline.project.application.dto;

import eu.venthe.pipeline.project.domain.ProjectId;
import eu.venthe.pipeline.project.domain.ProjectStatus;

public record CreateProjectSpecificationDto(ProjectId projectId, ProjectStatus status) {
}
