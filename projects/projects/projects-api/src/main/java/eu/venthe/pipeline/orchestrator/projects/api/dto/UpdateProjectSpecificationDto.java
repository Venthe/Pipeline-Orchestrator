package eu.venthe.pipeline.orchestrator.projects.api.dto;

import eu.venthe.pipeline.orchestrator.projects.shared_kernel.ProjectStatus;
import lombok.Value;

@Value
public class UpdateProjectSpecificationDto {
    ProjectStatus status;
    String projectName;
}
