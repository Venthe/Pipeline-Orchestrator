package eu.venthe.pipeline.orchestrator.projects.projects.domain;

public interface ProjectManagement {
    void synchronize();
    void archive();
    void setUnavailable();
}
