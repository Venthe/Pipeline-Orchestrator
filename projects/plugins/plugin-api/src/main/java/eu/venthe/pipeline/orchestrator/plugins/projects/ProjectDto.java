package eu.venthe.pipeline.orchestrator.plugins.projects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDto {
    private Status status;
    private String id;

    public enum Status {
        ACTIVE
    }
}
