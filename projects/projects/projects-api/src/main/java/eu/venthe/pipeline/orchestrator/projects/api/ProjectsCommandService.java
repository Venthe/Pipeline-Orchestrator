package eu.venthe.pipeline.orchestrator.projects.api;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.ProjectEvent;

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

    default void handleEvent(String projectId, ProjectEvent event) {
        throw new UnsupportedOperationException();
    }
}
