package eu.venthe.pipeline.orchestrator.projects.api;

import lombok.Value;

@Value
public class ReadProjectSourceConfigurationDto {
    String id;
    String sourceType;
}
