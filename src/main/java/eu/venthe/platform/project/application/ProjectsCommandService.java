package eu.venthe.platform.project.application;

import eu.venthe.platform.project.application.dto.CreateProjectSpecificationDto;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.project.domain.ProjectStatus;
import eu.venthe.platform.data_source_configuration.SourceConfigurationId;
import eu.venthe.platform.shared_kernel.git.GitRevision;

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
