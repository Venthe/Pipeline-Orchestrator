package eu.venthe.platform.source_configuration.domain.model;

import eu.venthe.platform.source_configuration.domain.plugins.template.SourceRepositoryName;

public record SourceOwnedRepositoryName(ConfigurationSourceId configurationSourceId, SourceRepositoryName sourceRepositoryId) {
}
