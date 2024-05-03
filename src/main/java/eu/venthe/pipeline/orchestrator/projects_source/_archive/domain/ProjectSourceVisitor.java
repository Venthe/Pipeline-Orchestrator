package eu.venthe.pipeline.orchestrator.projects_source._archive.domain;

@FunctionalInterface
public interface ProjectSourceVisitor<T> {
    T visit(String id, String sourceType);
}
