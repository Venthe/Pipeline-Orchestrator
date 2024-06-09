package eu.venthe.pipeline.orchestrator.organizations.domain.projects.api;

import eu.venthe.pipeline.orchestrator.organizations.domain.projects.domain.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.domain.model.ProjectId;

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
