package eu.venthe.platform.projects.domain;

public interface SourceConfigurationVisitor {
    void setInternalIdentifier(SourceConfigurationInternalIdentifier identifier);

    void setType(String type);
}
