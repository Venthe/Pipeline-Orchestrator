package eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model;

import lombok.Value;

import java.util.Locale;

/**
 * Unique identifier/name for the plugin matching the source system it supports
 */
@Value
public class SourceType {
    String value;

    public SourceType(String value) {
        this.value = value.toLowerCase(Locale.ROOT);
    }
}
