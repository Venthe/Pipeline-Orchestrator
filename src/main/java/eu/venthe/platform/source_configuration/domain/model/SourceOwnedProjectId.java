package eu.venthe.platform.source_configuration.domain.model;

import eu.venthe.platform.source_configuration.domain.plugins.template.SourceProjectId;

public record SourceOwnedProjectId(ConfigurationSourceId configurationSourceId, SourceProjectId sourceProjectId) {
}
