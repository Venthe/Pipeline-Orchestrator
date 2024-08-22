package eu.venthe.platform._archive.repositorys_and_orgs;

import eu.venthe.platform.shared_kernel.repository.RepositoryStatus;

public record UpdateRepositorySpecificationDto(RepositoryStatus status, String repositoryName) {
}
