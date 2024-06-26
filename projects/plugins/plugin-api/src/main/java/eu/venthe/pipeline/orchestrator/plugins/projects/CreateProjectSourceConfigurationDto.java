package eu.venthe.pipeline.orchestrator.plugins.projects;

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
