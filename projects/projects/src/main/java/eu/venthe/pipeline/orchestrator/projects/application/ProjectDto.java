package eu.venthe.pipeline.orchestrator.projects.application;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectDto {
    String name;
    String sourceId;
}
