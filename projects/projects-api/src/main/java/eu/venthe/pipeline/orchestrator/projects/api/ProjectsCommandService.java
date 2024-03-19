package eu.venthe.pipeline.orchestrator.projects.api;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ProjectsCommandService {

    default void add(CreateProjectSpecification newProjectDto) {
        throw new UnsupportedOperationException();
    }

    default void update(String projectId) {
        throw new UnsupportedOperationException();
    }

    default void archive(String projectId) {
        throw new UnsupportedOperationException();
    }

    default void makePublic(String projectId) {
        throw new UnsupportedOperationException();
    }

    default void executeManualWorkflow(String projectId, String workflowName) {
        throw new UnsupportedOperationException();
    }

    default void handleEvent(String projectId, Event event) {
        throw new UnsupportedOperationException();
    }
}
