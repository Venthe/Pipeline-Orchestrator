package eu.venthe.platform.source_configuration.domain.plugins.template;

import eu.venthe.platform.shared_kernel.repository.RepositoryStatus;

public record Repository(
        SourceRepositoryId sourceRepositoryId,
        RepositoryStatus status
) {
}
