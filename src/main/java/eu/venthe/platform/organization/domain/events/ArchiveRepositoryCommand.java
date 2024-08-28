package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepositoryName;

public class ArchiveRepositoryCommand implements DomainTrigger {
    private final SourceOwnedRepositoryName repositoryId;

    public ArchiveRepositoryCommand(final SourceOwnedRepositoryName repositoryId) {
        this.repositoryId = repositoryId;
    }

    @Override
    public String getType() {
        return "archive_repository";
    }
}
