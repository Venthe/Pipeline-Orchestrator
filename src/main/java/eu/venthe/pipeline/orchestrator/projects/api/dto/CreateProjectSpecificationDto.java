package eu.venthe.pipeline.orchestrator.projects.api.dto;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus;

import java.util.Optional;

public record CreateProjectSpecificationDto(ProjectId projectId, ProjectStatus status, Optional<String> description) {
    public CreateProjectSpecificationDto(ProjectId projectId, ProjectStatus status) {
        this(projectId, status, Optional.empty());
    }

    public CreateProjectSpecificationDto {
        if (description == null) {
            description = Optional.empty();
        }
    }
}
