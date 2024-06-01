package eu.venthe.pipeline.orchestrator.projects.projects.application;

import eu.venthe.pipeline.orchestrator.projects.projects.api.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

public interface ProjectsCommandService {

    default void add(String configurationId, CreateProjectSpecificationDto newProjectDto) {
        throw new UnsupportedOperationException();
    }

    default void synchronize(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void changeStatus(ProjectId projectId, ProjectStatus status) {
        throw new UnsupportedOperationException();
    }

    default void executeManualWorkflow(ProjectId projectId, String workflowName) {
        throw new UnsupportedOperationException();
    }

    default void handleVersionControlEvent(ProjectId projectId, SystemEvent event) {
        throw new UnsupportedOperationException();
    }

    default void registerManualWorkflow(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void unregisterManualWorkflow(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void registerTrackedBranch(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void unregisterTrackedBranch(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }
}
