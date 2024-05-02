package eu.venthe.pipeline.orchestrator.projects.api;

import eu.venthe.pipeline.orchestrator.projects.api.dto.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.api.dto.UpdateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.shared_kernel.ProjectStatus;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

public interface ProjectsCommandService {

    default void add(CreateProjectSpecificationDto newProjectDto) {
        throw new UnsupportedOperationException();
    }

    default void updateDetails(String projectId, UpdateProjectSpecificationDto updateProjectSpecification) {
        throw new UnsupportedOperationException();
    }

    default void changeStatus(String projectId, ProjectStatus status) {
        throw new UnsupportedOperationException();
    }

    default void executeManualWorkflow(String projectId, String workflowName) {
        throw new UnsupportedOperationException();
    }

    default void handleVersionControlEvent(String projectId, SystemEvent event) {
        throw new UnsupportedOperationException();
    }

    default void registerManualWorkflow(String projectId) {
        throw new UnsupportedOperationException();
    }

    default void unregisterManualWorkflow(String projectId) {
        throw new UnsupportedOperationException();
    }

    default void registerTrackedBranch(String projectId) {
        throw new UnsupportedOperationException();
    }

    default void unregisterTrackedBranch(String projectId) {
        throw new UnsupportedOperationException();
    }
}
