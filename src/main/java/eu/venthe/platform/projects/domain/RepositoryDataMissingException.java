package eu.venthe.platform.projects.domain;

public class RepositoryDataMissingException extends RuntimeException {
    public RepositoryDataMissingException(String projectName) {
        super("Repository data missing: " + projectName);
    }
}
