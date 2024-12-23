package eu.venthe.platform.projects.domain;

public interface SourceConfigurationVisitor {
    void setIdentifier(String identifier);

    void setType(String type);
}
