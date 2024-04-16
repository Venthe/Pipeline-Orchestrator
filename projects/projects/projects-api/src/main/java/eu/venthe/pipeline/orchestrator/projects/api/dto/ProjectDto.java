package eu.venthe.pipeline.orchestrator.projects.api.dto;

import eu.venthe.pipeline.orchestrator.projects.shared_kernel.ProjectStatus;
import lombok.Value;

@Value
public class ProjectDto {
    String name;
    String sourceId;
    ProjectStatus status;
}
