package eu.venthe.platform._archive.projects_and_orgs;

import eu.venthe.platform.shared_kernel.project.RepositoryStatus;

public record UpdateRepositorySpecificationDto(RepositoryStatus status, String repositoryName) {
}
