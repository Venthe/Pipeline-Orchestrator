package eu.venthe.pipeline.orchestrator.projects.api;

import lombok.Value;

@Value
public class AddProjectDto {
    String name;
    String sourceId;
}
