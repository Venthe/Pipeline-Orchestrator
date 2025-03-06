package eu.venthe.platform.projects.domain;

public class RepositoryDataMissingException extends RuntimeException {
    public RepositoryDataMissingException(SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier,
                                          ProjectCorrelationId projectCorrelationId) {
        super("Repository data missing: %s:%s".formatted(sourceConfigurationInternalIdentifier, projectCorrelationId));
    }
}
