package eu.venthe.platform.data_source_configuration.domain.plugins.template.model;

import eu.venthe.platform.project.domain.ProjectStatus;
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
