package eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application;

import lombok.Value;

import java.util.Map;

@Value
public class ProjectSourceConfigurationDto {
    String name;
    String sourcePluginId;
    Map<String, String> properties;
}
