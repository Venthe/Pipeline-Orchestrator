package eu.venthe.pipeline.orchestrator.projects.api.dto;

import eu.venthe.pipeline.orchestrator.projects_source.domain.ProjectStatus;
import lombok.Value;

@Value
public class ProjectDto {
    String name;
    String sourceId;
    ProjectStatus status;
}
