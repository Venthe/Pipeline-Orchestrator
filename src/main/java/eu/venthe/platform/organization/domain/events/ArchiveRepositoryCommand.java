package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepositoryId;

public class ArchiveRepositoryCommand implements DomainTrigger {
    private final SourceOwnedRepositoryId repositoryId;

    public ArchiveRepositoryCommand(final SourceOwnedRepositoryId repositoryId) {
        this.repositoryId = repositoryId;
    }

    @Override
    public String getType() {
        return "archive_repository";
    }
}
