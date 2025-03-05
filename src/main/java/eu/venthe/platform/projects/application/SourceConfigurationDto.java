package eu.venthe.platform.projects.application;

import eu.venthe.platform.projects.domain.SourceConfigurationInternalIdentifier;
import lombok.Builder;

@Builder
public record SourceConfigurationDto(SourceConfigurationInternalIdentifier internalIdentifier, String type) {
}
