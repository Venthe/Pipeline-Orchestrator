package eu.venthe.pipeline.orchestrator.projects_source.domain;

import lombok.Value;

import java.util.UUID;

/**
 * Unique identifier/name for the plugin matching the source system it supports
 */
@Value
public class SourceType {
    String value;
}
