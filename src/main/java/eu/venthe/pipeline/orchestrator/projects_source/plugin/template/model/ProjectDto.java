package eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model;

import eu.venthe.pipeline.orchestrator.projects_source.domain.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDto {
    private String id;
    private ProjectStatus status;
}
