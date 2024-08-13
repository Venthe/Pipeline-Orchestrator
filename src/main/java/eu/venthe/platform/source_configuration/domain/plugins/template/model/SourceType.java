package eu.venthe.platform.source_configuration.domain.plugins.template.model;

import java.util.Locale;

/**
 * Unique identifier/name for the plugin matching the source system it supports
 */
public record SourceType(String value) {
    public SourceType(String value) {
        this.value = value.toLowerCase(Locale.ROOT);
    }
}
