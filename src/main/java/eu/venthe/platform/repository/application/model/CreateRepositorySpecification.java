package eu.venthe.platform.repository.application.model;

import eu.venthe.platform.repository.domain.RepositoryId;
import eu.venthe.platform.shared_kernel.repository.RepositoryStatus;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;

public record CreateRepositorySpecification(RepositoryId repositoryId, ConfigurationSourceId configurationSourceId, RepositoryStatus status) {
}
