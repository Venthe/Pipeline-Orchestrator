package eu.venthe.pipeline.orchestrator.projects_source.adapter;

import eu.venthe.pipeline.orchestrator.projects.shared_kernel.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDto {
    private ProjectStatus status;
    private String id;
}
