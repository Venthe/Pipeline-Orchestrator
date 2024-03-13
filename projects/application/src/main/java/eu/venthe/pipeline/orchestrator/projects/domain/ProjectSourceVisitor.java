package eu.venthe.pipeline.orchestrator.projects.domain;

import java.util.HashSet;

public interface ProjectSourceVisitor<T> {
    T visit(ProjectSourceConfigurationId id, String sourceType, HashSet<String> es);
}
