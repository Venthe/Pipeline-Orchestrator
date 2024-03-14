package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
public class NewProjectDto {
    @EqualsAndHashCode.Include
    Id id;

    public record Id(String systemId, String id) {
    }
}
