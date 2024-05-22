package eu.venthe.pipeline.orchestrator.projects.api.dto;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus;
import lombok.Value;

@Value
public class UpdateProjectSpecificationDto {
    ProjectStatus status;
    String projectName;
}
