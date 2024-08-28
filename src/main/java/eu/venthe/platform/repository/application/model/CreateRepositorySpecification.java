package eu.venthe.platform.repository.application.model;

import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.shared_kernel.project.RepositoryStatus;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;

public record CreateRepositorySpecification(RepositoryName repositoryId, ConfigurationSourceId configurationSourceId, RepositoryStatus status) {
}
