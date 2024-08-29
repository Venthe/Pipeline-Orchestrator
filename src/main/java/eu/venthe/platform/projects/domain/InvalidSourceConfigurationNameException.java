package eu.venthe.platform.projects.domain;

public class InvalidSourceConfigurationNameException extends RuntimeException{
    public InvalidSourceConfigurationNameException(String name) {
        super("Source configuration name \"%s\" is not correct.".formatted(name));
    }
}
