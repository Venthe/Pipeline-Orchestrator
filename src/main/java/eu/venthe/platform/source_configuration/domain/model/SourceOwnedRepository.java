package eu.venthe.platform.source_configuration.domain.model;

import eu.venthe.platform.source_configuration.domain.plugins.template.Repository;

public record SourceOwnedRepository(SourceOwnedRepositoryName repositoryId, Repository repository) {
}
