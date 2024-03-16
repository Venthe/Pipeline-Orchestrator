package eu.venthe.pipeline.orchestrator.projects.api;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ProjectsCommandService {

    default void add(CreateProjectSpecification newProjectDto) {
        throw new UnsupportedOperationException();
    }

    default void update(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void archive(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void makePublic(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void executeManualWorkflow(ProjectId projectId, String workflowName) {
        throw new UnsupportedOperationException();
    }

    default void handleEvent(ProjectId projectId, ObjectNode event) {
        throw new UnsupportedOperationException();
    }
}
