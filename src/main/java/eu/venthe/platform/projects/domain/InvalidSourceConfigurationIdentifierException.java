package eu.venthe.platform.projects.domain;

public class InvalidSourceConfigurationIdentifierException extends RuntimeException{
    public InvalidSourceConfigurationIdentifierException(String identifier) {
        super("Source configuration identifier \"%s\" is not correct.".formatted(identifier));
    }
}
