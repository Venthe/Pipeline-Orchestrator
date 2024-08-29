package eu.venthe.platform.projects.application;

public class SourceConfigurationAlreadyExistsException extends RuntimeException {
    public SourceConfigurationAlreadyExistsException(String name) {
        super("Source configuration '%s' already exists".formatted(name));
    }
}
