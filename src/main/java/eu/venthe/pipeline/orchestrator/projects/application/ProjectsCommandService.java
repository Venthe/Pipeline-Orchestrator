package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.organizations.application.dto.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectStatus;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

import java.io.File;

public interface ProjectsCommandService {

    default void add(SourceConfigurationId configurationId, CreateProjectSpecificationDto newProjectDto) {
        throw new UnsupportedOperationException();
    }

    default void synchronize(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void changeStatus(ProjectId projectId, ProjectStatus status) {
        throw new UnsupportedOperationException();
    }

    default JobExecutionId executeManualWorkflow(ProjectId projectId, String ref, File workflowFile) {
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
