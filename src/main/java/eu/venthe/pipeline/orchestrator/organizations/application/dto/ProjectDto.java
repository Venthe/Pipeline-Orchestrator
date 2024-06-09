package eu.venthe.pipeline.orchestrator.organizations.application.dto;

import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectStatus;
import lombok.Value;

@Value
public class ProjectDto {
    String name;
    String configurationId;
    ProjectStatus status;
}
