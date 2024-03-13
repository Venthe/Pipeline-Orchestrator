package eu.venthe.pipeline.orchestrator.plugins.projects;

import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
public class ReadProjectSourceConfigurationDto {
    String id;
    String sourceType;
}
