package eu.venthe.pipeline.orchestrator.projects_provider.api;

import lombok.Value;

@Value
public class ReadProjectSourceConfigurationDto {
    String id;
    String sourceType;
}
