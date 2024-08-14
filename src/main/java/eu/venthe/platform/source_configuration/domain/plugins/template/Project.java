package eu.venthe.platform.source_configuration.domain.plugins.template;

import eu.venthe.platform.shared_kernel.project.ProjectStatus;

public record Project(
        SourceProjectId sourceProjectId,
        ProjectStatus status
) {
}
