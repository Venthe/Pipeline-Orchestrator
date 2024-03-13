package eu.venthe.pipeline.orchestrator.projects.domain;

import java.util.HashSet;

public interface ProjectSourceVisitor<T> {
    T visit(String id, String sourceType, HashSet<String> es);
}
