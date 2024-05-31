package eu.venthe.pipeline.orchestrator.projects.source_configuration.api;

import lombok.Value;

@Value
public class ReadProjectSourceConfigurationDto {
    String id;
    String sourceType;
}
