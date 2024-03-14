package eu.venthe.pipeline.orchestrator.plugins.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@AllArgsConstructor
public class ProjectDto {
    private Status status;
    private Id id;

    public enum Status {ACTIVE}

    @Value
    public static class Id {
        String id;
    }
}
