package eu.venthe.pipeline.orchestrator.projects.plugin.template.model;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
public class ProjectDto {
    private String id;
    private ProjectStatus status;
    private Optional<String> description = Optional.empty();

    public ProjectDto(String id, ProjectStatus status) {
        this.id = id;
        this.status = status;
    }
}
