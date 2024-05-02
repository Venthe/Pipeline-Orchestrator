package eu.venthe.pipeline.orchestrator.projects_source;

import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Value
@SuperBuilder
public class CreateProjectSourceConfigurationDto {
    String id;
    String sourceType;
    Map<String, String> properties;
}
