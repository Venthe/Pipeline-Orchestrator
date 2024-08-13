package eu.venthe.pipeline._archive.projects_and_orgs;

import eu.venthe.pipeline.project.domain.ProjectStatus;

public record UpdateProjectSpecificationDto(ProjectStatus status, String projectName) {
}
