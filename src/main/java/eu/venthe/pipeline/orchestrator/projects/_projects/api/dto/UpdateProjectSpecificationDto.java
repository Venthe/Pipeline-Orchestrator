package eu.venthe.pipeline.orchestrator.projects._projects.api.dto;

import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectStatus;
import lombok.Value;

@Value
public class UpdateProjectSpecificationDto {
    ProjectStatus status;
    String projectName;
}
