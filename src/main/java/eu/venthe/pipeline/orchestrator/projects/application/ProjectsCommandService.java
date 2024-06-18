package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.organizations.application.dto.CreateProjectSpecificationDto;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectStatus;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;

public interface ProjectsCommandService {

    void add(SourceConfigurationId configurationId, CreateProjectSpecificationDto newProjectDto);

    default void synchronize(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void changeStatus(ProjectId projectId, ProjectStatus status) {
        throw new UnsupportedOperationException();
    }

    default void handleEvent(ProjectId projectId, ProjectEvent event) {
        throw new UnsupportedOperationException();
    }

    void registerTrackedRevision(ProjectId projectId, Revision revision);

    default void unregisterTrackedRevision(ProjectId projectId, Revision revision) {
        throw new UnsupportedOperationException();
    }
}
