package eu.venthe.pipeline.orchestrator.projects.application;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
public class CreateProjectSpecification {
    @EqualsAndHashCode.Include
    Id id;

    public record Id(String systemId, String id) {
    }
}
