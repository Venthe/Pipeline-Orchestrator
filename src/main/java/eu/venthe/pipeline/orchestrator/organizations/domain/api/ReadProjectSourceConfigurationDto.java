package eu.venthe.pipeline.orchestrator.organizations.domain.api;

import lombok.Value;

@Value
public class ReadProjectSourceConfigurationDto {
    String id;
    String sourceType;
}
