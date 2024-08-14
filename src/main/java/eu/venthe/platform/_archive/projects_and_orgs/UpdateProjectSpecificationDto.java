package eu.venthe.platform._archive.projects_and_orgs;

import eu.venthe.platform.shared_kernel.project.ProjectStatus;

public record UpdateProjectSpecificationDto(ProjectStatus status, String projectName) {
}
