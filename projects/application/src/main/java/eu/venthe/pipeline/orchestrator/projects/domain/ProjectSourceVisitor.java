package eu.venthe.pipeline.orchestrator.projects.domain;

@FunctionalInterface
public interface ProjectSourceVisitor<T> {
    T visit(String id, String sourceType);
}
