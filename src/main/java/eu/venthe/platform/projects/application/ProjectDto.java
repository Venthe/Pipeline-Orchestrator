package eu.venthe.platform.projects.application;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ProjectDto(String sourceName, String name, ZonedDateTime lastUpdated) {
}
