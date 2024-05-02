package eu.venthe.pipeline.orchestrator.projects_source.domain;

@FunctionalInterface
public interface ProjectSourceVisitor<T> {
    T visit(String id, String sourceType);
}
