package eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model;

import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class ProjectDto {
    private Id id;
    private ProjectStatus status;
    private Optional<String> description = Optional.empty();

    public ProjectDto(String id, ProjectStatus status) {
        this.id = new Id(id);
        this.status = status;
    }

    public record Id(String id) {
    }
}