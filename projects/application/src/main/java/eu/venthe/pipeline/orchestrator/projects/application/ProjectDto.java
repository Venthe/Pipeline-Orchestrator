package eu.venthe.pipeline.orchestrator.projects.application;

import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
public class ProjectDto {
    String name;
    String sourceId;
}
