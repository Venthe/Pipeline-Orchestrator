package eu.venthe.platform.projects.domain;

public record SourceConfigurationInternalIdentifier(String value) {
    public SourceConfigurationInternalIdentifier {
        if (value == null || value.isBlank()) {
            throw new InvalidSourceConfigurationIdentifierException(value);
        }
    }
}
