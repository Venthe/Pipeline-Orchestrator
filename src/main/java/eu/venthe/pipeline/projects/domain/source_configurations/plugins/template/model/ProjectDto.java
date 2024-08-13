package eu.venthe.pipeline.projects.domain.source_configurations.plugins.template.model;

import eu.venthe.pipeline.projects.domain.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDto {
    private Id id;
    private ProjectStatus status;

    public ProjectDto(String id, ProjectStatus status) {
        this.id = new Id(id);
        this.status = status;
    }

    public record Id(String id) {
    }
}
