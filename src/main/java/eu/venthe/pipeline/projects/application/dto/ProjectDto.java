package eu.venthe.pipeline.projects.application.dto;

import eu.venthe.pipeline.projects.domain.ProjectStatus;
import lombok.Value;

@Value
public class ProjectDto {
    String name;
    String configurationId;
    ProjectStatus status;
}
