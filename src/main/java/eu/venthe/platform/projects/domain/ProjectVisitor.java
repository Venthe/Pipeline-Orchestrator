package eu.venthe.platform.projects.domain;

public interface ProjectVisitor {
    void setSourceConfigurationName(String sourceConfigurationName);

    void setName(String name);
}
