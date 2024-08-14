package eu.venthe.platform.project.application.dto;

import eu.venthe.platform.shared_kernel.project.ProjectStatus;
import lombok.Value;

@Value
public class ProjectDto {
    String name;
    String configurationId;
    ProjectStatus status;
}
