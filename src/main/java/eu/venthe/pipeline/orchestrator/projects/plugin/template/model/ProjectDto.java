package eu.venthe.pipeline.orchestrator.projects.plugin.template.model;

import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDto {
    private String id;
    private ProjectStatus status;
}
