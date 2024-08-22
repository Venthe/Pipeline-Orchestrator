package eu.venthe.platform.organization.domain.events;

import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepositoryId;

public class SynchronizeRepositoryCommand implements DomainTrigger {
    private final SourceOwnedRepositoryId repositoryId;

    public SynchronizeRepositoryCommand(final SourceOwnedRepositoryId repositoryId) {
        this.repositoryId = repositoryId;
    }

    @Override
    public String getType() {
        return "synchronize_repository";
    }
}
