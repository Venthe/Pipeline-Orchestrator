package eu.venthe.pipeline.orchestrator.organizations.domain.projects.api;

import eu.venthe.pipeline.orchestrator.organizations.domain.projects.domain.model.ProjectStatus;
import lombok.Value;

@Value
public class ProjectDto {
    String name;
    String configurationId;
    ProjectStatus status;
}
