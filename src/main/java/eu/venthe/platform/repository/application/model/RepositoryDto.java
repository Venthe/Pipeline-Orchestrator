package eu.venthe.platform.repository.application.model;

import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.shared_kernel.project.RepositoryStatus;

public record RepositoryDto(RepositoryName repositoryId, RepositoryStatus status) {
}
