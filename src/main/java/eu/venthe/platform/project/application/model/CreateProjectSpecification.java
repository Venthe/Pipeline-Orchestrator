package eu.venthe.platform.project.application.model;

import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.shared_kernel.project.ProjectStatus;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;

public record CreateProjectSpecification(ProjectId projectId, ConfigurationSourceId configurationSourceId, ProjectStatus status) {
}
