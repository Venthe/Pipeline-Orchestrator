package eu.venthe.pipeline.orchestrator.plugins.projects;

import eu.venthe.pipeline.orchestrator.shared_kernel.projects.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDto {
    private ProjectStatus status;
    private String id;
}
