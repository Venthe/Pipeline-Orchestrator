package eu.venthe.platform.repository.application.model;

import eu.venthe.platform.repository.domain.RepositoryId;
import eu.venthe.platform.shared_kernel.repository.RepositoryStatus;
import lombok.Value;

@Value
public record RepositoryDto(RepositoryId repositoryId, RepositoryStatus status) {
}
