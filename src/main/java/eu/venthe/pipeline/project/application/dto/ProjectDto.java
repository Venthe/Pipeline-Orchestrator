package eu.venthe.pipeline.project.application.dto;

import eu.venthe.pipeline.project.domain.ProjectStatus;
import lombok.Value;

@Value
public class ProjectDto {
    String name;
    String configurationId;
    ProjectStatus status;
}
