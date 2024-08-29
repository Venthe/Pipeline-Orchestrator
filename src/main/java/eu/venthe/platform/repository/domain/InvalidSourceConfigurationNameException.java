package eu.venthe.platform.repository.domain;

public class InvalidSourceConfigurationNameException extends RuntimeException{
    public InvalidSourceConfigurationNameException(String name) {
        super("Source configuration name \"%s\" is not correct.".formatted(name));
    }
}
