package eu.venthe.platform.source_configuration.domain.model;

import eu.venthe.platform.source_configuration.domain.plugins.template.Project;

public record SourceOwnedProject(SourceOwnedProjectId projectId, Project project) {
}
