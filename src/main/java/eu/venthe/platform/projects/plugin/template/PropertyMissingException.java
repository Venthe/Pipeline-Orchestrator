package eu.venthe.platform.projects.plugin.template;

public class PropertyMissingException extends RuntimeException {
    public PropertyMissingException(String property) {
        super("Property '%s' is required".formatted(property));
    }
}
