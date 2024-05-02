package eu.venthe.pipeline.orchestrator.projects_source.api;

import lombok.Value;

@Value
public class ReadProjectSourceConfigurationDto {
    String id;
    String sourceType;
}
