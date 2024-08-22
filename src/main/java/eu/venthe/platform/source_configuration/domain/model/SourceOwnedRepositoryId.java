package eu.venthe.platform.source_configuration.domain.model;

import eu.venthe.platform.source_configuration.domain.plugins.template.SourceRepositoryId;

public record SourceOwnedRepositoryId(ConfigurationSourceId configurationSourceId, SourceRepositoryId sourceRepositoryId) {
}
