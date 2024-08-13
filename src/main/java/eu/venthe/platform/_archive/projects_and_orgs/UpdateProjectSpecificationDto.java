package eu.venthe.platform._archive.projects_and_orgs;

import eu.venthe.platform.project.domain.ProjectStatus;

public record UpdateProjectSpecificationDto(ProjectStatus status, String projectName) {
}
