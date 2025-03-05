package eu.venthe.platform.projects.domain;

public class SourceConfigurationMissingException extends RuntimeException {
    public SourceConfigurationMissingException(String sourceName) {
        super("Source configuration missing: " + sourceName);
    }
}
