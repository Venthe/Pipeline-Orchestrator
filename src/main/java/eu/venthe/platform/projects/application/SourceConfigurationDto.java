package eu.venthe.platform.projects.application;

import lombok.Builder;

@Builder
public record SourceConfigurationDto(String identifier, String type) {
}
