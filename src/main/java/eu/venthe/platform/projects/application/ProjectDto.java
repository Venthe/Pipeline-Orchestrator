package eu.venthe.platform.projects.application;

import lombok.Builder;

@Builder
public record ProjectDto(String sourceName, String name) {
}
