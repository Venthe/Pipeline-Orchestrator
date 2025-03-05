package eu.venthe.platform.projects.application;

import eu.venthe.platform.projects.domain.SourceConfigurationInternalIdentifier;

public class SourceConfigurationAlreadyExistsException extends RuntimeException {
    public SourceConfigurationAlreadyExistsException(SourceConfigurationInternalIdentifier identifier) {
        super("Source configuration '%s' already exists".formatted(identifier));
    }
}
