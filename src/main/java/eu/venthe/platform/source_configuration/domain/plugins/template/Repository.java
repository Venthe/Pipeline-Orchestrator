package eu.venthe.platform.source_configuration.domain.plugins.template;

import eu.venthe.platform.shared_kernel.project.RepositoryStatus;

public record Repository(
        SourceRepositoryName sourceRepositoryId,
        RepositoryStatus status
) {
}
