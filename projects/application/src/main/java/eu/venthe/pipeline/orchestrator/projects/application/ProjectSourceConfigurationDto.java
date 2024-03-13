package eu.venthe.pipeline.orchestrator.projects.application;

import lombok.Value;

import java.util.Map;

@Value
public class ProjectSourceConfigurationDto {
    String name;
    String sourcePluginId;
    Map<String, String> properties;
}
