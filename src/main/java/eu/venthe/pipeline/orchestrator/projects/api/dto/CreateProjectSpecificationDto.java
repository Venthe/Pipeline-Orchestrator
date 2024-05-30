package eu.venthe.pipeline.orchestrator.projects.api.dto;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus;

import java.util.Optional;

public record CreateProjectSpecificationDto(ProjectId projectId, ProjectStatus status, Optional<String> description) {
}
