package eu.venthe.platform.projects.application;

import eu.venthe.platform.projects.domain.SourceConfigurationInternalIdentifier;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ProjectDto(
        SourceConfigurationInternalIdentifier sourceInternalIdentifier,
        String name,
        ZonedDateTime lastUpdated
) {
}
