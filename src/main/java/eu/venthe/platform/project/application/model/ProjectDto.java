package eu.venthe.platform.project.application.model;

import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.shared_kernel.project.ProjectStatus;
import lombok.Value;

@Value
public class ProjectDto {
    ProjectId projectId;
    ProjectStatus status;
}
