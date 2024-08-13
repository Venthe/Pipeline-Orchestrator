package eu.venthe.platform.project.application.dto;

import eu.venthe.platform.project.domain.ProjectStatus;
import lombok.Value;

@Value
public class ProjectDto {
    String name;
    String configurationId;
    ProjectStatus status;
}
