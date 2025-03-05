package eu.venthe.platform.projects.domain;

public class SourceConfigurationMissingException extends RuntimeException {
    public SourceConfigurationMissingException(SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier) {
        super("Source configuration missing: " + sourceConfigurationInternalIdentifier);
    }
}
