package eu.venthe.pipeline.orchestrator.projects.application.dto;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectStatus;
import lombok.Value;

@Value
public class ProjectDto {
    String name;
    String configurationId;
    ProjectStatus status;
}
