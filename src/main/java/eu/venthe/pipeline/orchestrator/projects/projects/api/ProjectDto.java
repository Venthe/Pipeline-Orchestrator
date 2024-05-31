package eu.venthe.pipeline.orchestrator.projects.projects.api;

import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectStatus;
import lombok.Value;

@Value
public class ProjectDto {
    String name;
    String configurationId;
    ProjectStatus status;
}
