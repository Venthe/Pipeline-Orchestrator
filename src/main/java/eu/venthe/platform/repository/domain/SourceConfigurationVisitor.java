package eu.venthe.platform.repository.domain;

public interface SourceConfigurationVisitor {
    void setName(String name);

    void setType(String type);
}
