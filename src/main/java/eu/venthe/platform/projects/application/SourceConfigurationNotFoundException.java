package eu.venthe.platform.projects.application;

import eu.venthe.platform.projects.domain.SourceConfigurationInternalIdentifier;

public class SourceConfigurationNotFoundException extends RuntimeException {
    public SourceConfigurationNotFoundException(SourceConfigurationInternalIdentifier sourceIdentifier) {
        super("Source configuration '%s' not found".formatted(sourceIdentifier));
    }
}
