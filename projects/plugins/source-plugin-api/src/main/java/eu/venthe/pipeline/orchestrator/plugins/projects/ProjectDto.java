package eu.venthe.pipeline.orchestrator.plugins.projects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDto {
    private ProjectStatus status;
    private String id;
}
