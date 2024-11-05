package eu.venthe.platform.projects.application;

public class SourceConfigurationNotFoundException extends RuntimeException {
    public SourceConfigurationNotFoundException(String sourceName) {
        super("Source configuration '%s' not found".formatted(sourceName));
    }
}
