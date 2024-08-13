package eu.venthe.platform.organization.domain.source_configuration.plugins.template.model;

import eu.venthe.platform.project.domain.ProjectStatus;

public record ProjectDto(ProjectId id, ProjectStatus status) {
    public ProjectDto(String id, ProjectStatus status) {
        this(new ProjectId(id), status);
    }
}
