package eu.venthe.pipeline.orchestrator.projects.domain;

public interface ProjectSourceVisitor<T> {
    T visit();
}
