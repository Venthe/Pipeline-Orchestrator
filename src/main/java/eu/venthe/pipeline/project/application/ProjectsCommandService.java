package eu.venthe.pipeline.project.application;

import eu.venthe.pipeline.project.application.dto.CreateProjectSpecificationDto;
import eu.venthe.pipeline.project.domain.ProjectId;
import eu.venthe.pipeline.project.domain.ProjectStatus;
import eu.venthe.pipeline.project.domain.source_configurations.SourceConfigurationId;
import eu.venthe.pipeline.shared_kernel.git.GitRevision;

public interface ProjectsCommandService {

    void add(SourceConfigurationId configurationId, CreateProjectSpecificationDto newProjectDto);

    default void synchronize(ProjectId projectId) {
        throw new UnsupportedOperationException();
    }

    default void changeStatus(ProjectId projectId, ProjectStatus status) {
        throw new UnsupportedOperationException();
    }

    void registerTrackedRevision(ProjectId projectId, GitRevision revision);

    default void unregisterTrackedRevision(ProjectId projectId, GitRevision revision) {
        throw new UnsupportedOperationException();
    }
}
