package eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain;

public interface ProjectSourceVisitor<T> {
    T visit();
}
