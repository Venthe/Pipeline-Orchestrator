package eu.venthe.pipeline.orchestrator.projects.api;

import lombok.Value;

@Value
public class CreateProjectSpecification {
    Id id;

    public record Id(String systemId, String id) {
    }
}
