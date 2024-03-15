package eu.venthe.pipeline.orchestrator.projects_source.api;

import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
public class ReadProjectSourceConfigurationDto {
    String id;
    String sourceType;
}
